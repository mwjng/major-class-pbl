package com.example.android_sns

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ListAdapter(val context: Context?, val UserList: ArrayList<User>) : BaseAdapter() {

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
        // 이미지 추가
        val view : View = LayoutInflater.from(parent?.context).inflate(R.layout.card, null)
        val title = view.findViewById<TextView>(R.id.time_title)
        val name = view.findViewById<TextView>(R.id.time_name)
        val content = view.findViewById<TextView>(R.id.time_content)
        val like = view.findViewById<TextView>(R.id.time_count)
        val date = view.findViewById<TextView>(R.id.time_date)
        val img = view.findViewById<ImageView>(R.id.content_img)

        val user = UserList[count]
        title.text = user.title
        name.text = user.name
        content.text = user.content
        like.text = user.like.toString()
        date.text = user.date

        val imgRef = Firebase.storage.reference.child("images/${user.writer}_${user.date}")

        imgRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            img.setImageBitmap(bmp)
        }

        view.findViewById<Button>(R.id.timeID).setOnClickListener {
            like.text = (user.like+1).toString()
            val db: FirebaseFirestore = Firebase.firestore
            val itemsCollectionRef = db.collection("users")
            itemsCollectionRef.document(user.writer).collection("upload").document(user.id).update("like", user.like+1)
                .addOnSuccessListener {  }
                .addOnFailureListener {  }
            Toast.makeText(context, "좋아요", Toast.LENGTH_SHORT).show();
        }

        return view
    }

}