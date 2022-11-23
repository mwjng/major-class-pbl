package com.example.android_sns

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FriendsAdapter (val context: Context?, val UserList: ArrayList<Friend>) : BaseAdapter(){

        override fun getCount(): Int {
            return UserList.size
        }
        override fun getItem(count: Int): Any {
            return UserList[count]
        }
        override fun getItemId(i: Int): Long {
            return 0
        }

        override fun getView(count: Int, convertView: View?, parent: ViewGroup?): View {
            val db: FirebaseFirestore = Firebase.firestore
            val itemsCollectionRef = db.collection("users")
            val view : View = LayoutInflater.from(parent?.context).inflate(R.layout.friends, null)
            val name = view.findViewById<TextView>(R.id.friendname)
            val user = UserList[count]
            name.text = user.nickname

            view.findViewById<Button>(R.id.fprofile).setOnClickListener {
                val intent = Intent(context, FriendProfile::class.java)
                intent.putExtra("key",user.email)
                startActivity(view.context,intent,null)
            }

            return view
        }

}
