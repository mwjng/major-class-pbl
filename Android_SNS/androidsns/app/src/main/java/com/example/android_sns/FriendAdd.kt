package com.example.android_sns

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_sns.databinding.ActivityFriendaddBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FriendAdd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFriendaddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db: FirebaseFirestore = Firebase.firestore
        val itemsCollectionRef = db.collection("users")
        val list = arrayListOf<Member>()

        binding.searchBtn.setOnClickListener {
            val target = binding.targetName.text.toString()
            itemsCollectionRef.get().addOnSuccessListener {
                for(doc in it) {
                    if (target.equals(doc.id)) {
                        list.add(Member(doc["image"].toString().toInt(), doc["email"].toString(), doc["nickname"].toString(), doc["message"].toString()))
                        break
                    }
                }
                var Adapter = SearchAdapter(this, list)
                binding.list3.adapter = Adapter
            }
            // 사진 / 이름
        }
    }
}