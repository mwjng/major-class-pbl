package com.example.android_sns

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.android_sns.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val binding by lazy{ActivityMainBinding.inflate(layoutInflater)}

    private lateinit var timeline: Timeline
    private lateinit var profile: Profile
    private lateinit var friends: Friends

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Manifest.permission.READ_EXTERNAL_STORAGE
        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)

        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        val intent = getIntent()
        val key = intent.getStringExtra("key").toString()

        if (key.equals("remove")) {
            friends = Friends().newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.frm, friends).commit()
        }
        else {
            timeline = Timeline().newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.frm, timeline).commit()
        }

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
            R.id.write -> {
                startActivity(
                    Intent(this, WriteActivity::class.java)
                )
                finish()
            }
            R.id.change -> {
                startActivity(
                    Intent(this, Account::class.java)
                )
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestPermission(perm: String) {
        if (checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED)
            return

        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val noPerms = it.filter { item -> item.value == false }.keys
            if (noPerms.isNotEmpty()) { // there is a permission which is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    //setMessage(getString(R.string.no_permission, noPerms.toString()))
                }.show()
            }
        }

        if (shouldShowRequestPermissionRationale(perm)) {
            // you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                //setMessage(getString(R.string.req_permission_reason, perm))
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(arrayOf(perm)) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // should be called in onCreate()
            requestPermLauncher.launch(arrayOf(perm))
        }
    }

}