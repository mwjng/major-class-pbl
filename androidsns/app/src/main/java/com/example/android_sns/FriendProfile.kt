package com.example.android_sns

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_sns.databinding.ActivityFriendprofileBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class FriendProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFriendprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db: FirebaseFirestore = Firebase.firestore
        val itemsCollectionRef = db.collection("users")


        val intent = getIntent()
        val email = intent.getStringExtra("key").toString()

        val imgRef = Firebase.storage.reference.child("images/${email}PROFILE")
        imgRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            binding.fprofileImg.setImageBitmap(bmp)
        }

        var UserList = arrayListOf<User>()

        itemsCollectionRef.document(email).get()
            .addOnSuccessListener {
                binding.fprofileName.setText(it["nickname"].toString())
                binding.fprofileMsg.setText(it["message"].toString())
            }.addOnFailureListener{}

        itemsCollectionRef.document(email).collection("upload").get().addOnSuccessListener {
            for (doc in it) {
                UserList.add(User(doc["image"].toString().toInt(), email, doc.id, doc["title"].toString(), doc["nickname"].toString(),
                    doc["content"].toString(), doc["like"].toString().toInt(), doc["date"].toString()))
            }
            UserList.sortByDescending { it.date }
            var Adapter = ListAdapter(this, UserList)
            binding.flist.adapter = Adapter
        }

    }
}