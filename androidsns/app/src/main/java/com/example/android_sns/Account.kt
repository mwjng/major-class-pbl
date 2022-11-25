package com.example.android_sns

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_sns.databinding.ActivityAccountBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.change.setOnClickListener {
            val userEmail = binding.username2.text.toString()
            val password = binding.password2.text.toString()
            updateAccount(userEmail, password)
        }

        binding.delete.setOnClickListener {
            deleteAccount()
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun updateAccount(email: String, pwd: String) {
        Firebase.auth.currentUser?.updateEmail(email)
            ?.addOnCompleteListener(this) {
                Firebase.auth.sendPasswordResetEmail(pwd)
                    ?.addOnCompleteListener(this) {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
            }
    }

    private fun deleteAccount() {
        Firebase.auth.currentUser?.delete()
    }
}