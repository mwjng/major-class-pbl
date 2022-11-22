package com.example.android_sns

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.appcompat.app.AppCompatActivity
import com.example.android_sns.databinding.ActivitySignupBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancel2.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.confirm2.setOnClickListener {
            var nickname = binding.nickname2.text.toString()
            val userEmail = binding.username2.text.toString()
            val password = binding.password2.text.toString()
            doSignup(userEmail, password, nickname)
        }
    }

    private fun doSignup(userEmail: String, password: String, nickname: String) {
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val db: FirebaseFirestore = Firebase.firestore
                    val itemMap = hashMapOf(
                        "nickname" to nickname,
                        "message" to ""
                    )
                    db.collection("users").document(userEmail).set(itemMap)
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    finish()
                } else {
                    it.exception?.message
                    println("Signup Failed")
                }
            }
    }
}