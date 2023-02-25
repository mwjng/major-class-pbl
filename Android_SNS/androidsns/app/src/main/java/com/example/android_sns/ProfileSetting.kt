package com.example.android_sns

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android_sns.databinding.ActivityProfilesettingBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileSetting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfilesettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // storage rule은 인증이 된 경우에만 접근 가능하도록 되어있음
        Firebase.auth.currentUser ?: finish()

        val db: FirebaseFirestore = Firebase.firestore
        val itemsCollectionRef = db.collection("users")

        binding.confirm.setOnClickListener {
            // 프로필 수정 작업
            val nickName = binding.nickname.text
            val message = binding.message.text
            val email = Firebase.auth.currentUser?.email.toString()

            itemsCollectionRef.document(email).update("nickname", nickName.toString())
            itemsCollectionRef.document(email).update("message", message.toString())

            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.cancel.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}