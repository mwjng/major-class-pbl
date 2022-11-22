package com.example.android_sns

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class FriendsAdapter (val context: Context?, val UserList: ArrayList<User>) : BaseAdapter(){

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
            val view : View = LayoutInflater.from(parent?.context).inflate(R.layout.friends, null)
            val name = view.findViewById<TextView>(R.id.friendname)
            val user = UserList[count]
            name.text = user.name

            return view
        }

}
