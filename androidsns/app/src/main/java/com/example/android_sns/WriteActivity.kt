package com.example.android_sns

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.android_sns.databinding.ActivityWriteBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WriteActivity : AppCompatActivity(){
    val db: FirebaseFirestore = Firebase.firestore
    val email = Firebase.auth.currentUser?.email.toString()
    val itemsCollectionRef = db.collection("users")
    var name = ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemsCollectionRef.document(email).get()
            .addOnSuccessListener {
                name = it["nickname"].toString()
            }.addOnFailureListener {
            }

        binding.canc.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.conf.setOnClickListener {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")
            val formatter2 = DateTimeFormatter.ofPattern("yyyyMMddhhmmss")
            val formatted = current.format(formatter)
            val formatted2 = current.format(formatter2)
            val nickname = name
            val title = binding.editTitle.text
            val content = binding.editText.text
            val like = 0
            val date = formatted
            val itemMap = hashMapOf(
                // 이미지 필요
                "nickname" to nickname.toString(),
                "title" to title.toString(),
                "content" to content.toString(),
                "like" to like,
                "date" to date.toString()
            )
            db.collection("users").document(email).collection("upload").document(formatted2)
                .set(itemMap)
                .addOnSuccessListener {  }
                .addOnFailureListener {  }

            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}