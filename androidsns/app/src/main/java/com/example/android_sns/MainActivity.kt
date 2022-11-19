package com.example.android_sns

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.android_sns.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var timeline: Timeline
    private lateinit var profile: Profile
    private lateinit var friends: Friends
    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        timeline = Timeline().newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.frm, timeline).commit()

        val onBottomItemSelectedListener =
            NavigationBarView.OnItemSelectedListener {
                when (it.itemId) {
                    R.id.timeline -> {
                        timeline = Timeline().newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frm, timeline).commit()
                        true
                    }
                    R.id.profile -> {
                        profile = Profile().newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frm, profile).commit()
                        true

                    }
                    R.id.friends -> {
                        friends = Friends().newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frm, friends).commit()
                        true

                    }
                    R.id.settings -> {
                        settings = Settings().newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frm, settings).commit()
                        true

                    }
                    else -> false
                }

            }
        binding.bottomNavi.setOnItemSelectedListener(onBottomItemSelectedListener)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signout -> {
                Firebase.auth.signOut()
                startActivity(
                    Intent(this, LoginActivity::class.java)
                )
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}