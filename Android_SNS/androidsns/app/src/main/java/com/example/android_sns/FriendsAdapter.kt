package com.example.android_sns

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class FriendsAdapter (val context: Context?, val UserList: ArrayList<Friend>) : BaseAdapter(){

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
            val view : View = LayoutInflater.from(parent?.context).inflate(R.layout.friends, null)
            val proImg = view.findViewById<ImageView>(R.id.imageViewf)
            val name = view.findViewById<TextView>(R.id.friendname)
            val user = UserList[count]
            name.text = user.nickname

            val imgRef = Firebase.storage.reference.child("images/${user.email}PROFILE")
            imgRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                proImg.setImageBitmap(bmp)
            }

            view.findViewById<Button>(R.id.fprofile).setOnClickListener {
                val intent = Intent(context, FriendProfile::class.java)
                intent.putExtra("key",user.email)
                startActivity(view.context,intent,null)
            }

            view.findViewById<Button>(R.id.remove).setOnClickListener {
                itemsCollectionRef.document(email).collection("friends").document(user.email).delete()
                Toast.makeText(context, "친구목록에서 삭제하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("key", "remove")
                startActivity(view.context,intent,null)
            }

            return view
        }

}
