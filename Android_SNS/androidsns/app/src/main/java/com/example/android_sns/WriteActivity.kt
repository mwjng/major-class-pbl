package com.example.android_sns

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.android_sns.databinding.ActivityWriteBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WriteActivity : AppCompatActivity(){
    val db: FirebaseFirestore = Firebase.firestore
    val storage = Firebase.storage
    val email = Firebase.auth.currentUser?.email.toString()
    val itemsCollectionRef = db.collection("users")
    var name = ""
    var imgUri=""
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

        val img = binding.userImage
        val readImg = registerForActivityResult(ActivityResultContracts.GetContent()) {
            img.setImageURI(it)
            imgUri = it.toString()
        }
        binding.uploadIMG.setOnClickListener {
            readImg.launch("image/*")
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
                "image" to 0,
                "writer" to email,
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

            // storage에 이메일_date로 저장
            val imgRef = storage.reference.child("images/${email}_${date}")
            if (imgUri.toUri() != null) {
                imgRef.putFile(imgUri.toUri()).addOnCompleteListener{
                    //Toast.makeText(context, "업로드 대따", Toast.LENGTH_SHORT).show();
                }
            }

            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}