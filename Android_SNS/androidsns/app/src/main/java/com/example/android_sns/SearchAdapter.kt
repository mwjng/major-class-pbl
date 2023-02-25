package com.example.android_sns

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class SearchAdapter (val context: Context?, val UserList: ArrayList<Member>) : BaseAdapter(){

    override fun getCount(): Int {
        return UserList.size
    }
    override fun getItem(count: Int): Any {
        return UserList[count]
    }
    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(count: Int, convertView: View?, parent: ViewGroup?): View {
        val db: FirebaseFirestore = Firebase.firestore
        val email = Firebase.auth.currentUser?.email.toString()
        val itemsCollectionRef = db.collection("users")
        val view : View = LayoutInflater.from(parent?.context).inflate(R.layout.search_user, null)
        val img = view.findViewById<ImageView>(R.id.imageView3)
        val name = view.findViewById<TextView>(R.id.userName)
        val user = UserList[count]
        name.text = user.nickname

        val imgRef = Firebase.storage.reference.child("images/${user.email}PROFILE")
        imgRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            img.setImageBitmap(bmp)
        }

        view.findViewById<Button>(R.id.add).setOnClickListener {
            if (!email.equals(user.email)) {
                val itemMap = hashMapOf(
                    "image" to user.image,
                    "friends" to user.email,
                    "nickname" to user.nickname
                )
                itemsCollectionRef.document(email).collection("friends").document(user.email)
                    .set(itemMap)
                    .addOnSuccessListener { }
                    .addOnFailureListener { }

                var boo = false
                itemsCollectionRef.document(email).get()
                    .addOnSuccessListener {
                        boo = it["friend"].toString().toBoolean()
                    }.addOnFailureListener {}

                if (!boo) {
                    itemsCollectionRef.document(email).update("friend", true)
                        .addOnSuccessListener {}
                }

                Toast.makeText(context, user.nickname + "를 친구로 추가했습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "사용자 본인입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

}