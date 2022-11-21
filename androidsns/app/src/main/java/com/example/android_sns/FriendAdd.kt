package com.example.android_sns

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_sns.databinding.ActivityFriendaddBinding
import com.example.android_sns.databinding.ActivityProfilesettingBinding

class FriendAdd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFriendaddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val targetName = binding.targetName.text

        binding.searchBtn.setOnClickListener {
            // targetName = 검색할 이름
            // 버튼 누르면 이름이 일치하는 사용자 목록을 보여줌 <-- 코딩 필요
            // 사진 / 이름 / 상태메세지 /
            // 사용자를 누르면 프로필을 보여줌
            // 프로필에서 메뉴를 누르면 추가 버튼을 누를 수 있음
            Toast.makeText(this, "targetName : "+targetName, Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}