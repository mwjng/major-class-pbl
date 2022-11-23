package com.example.android_sns

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(val context: Context?, val UserList: ArrayList<User>) : BaseAdapter() {

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
        // 이미지 추가
        val view : View = LayoutInflater.from(parent?.context).inflate(R.layout.card, null)
        val title = view.findViewById<TextView>(R.id.time_title)
        val name = view.findViewById<TextView>(R.id.time_name)
        val content = view.findViewById<TextView>(R.id.time_content)
        val like = view.findViewById<TextView>(R.id.time_count)
        val date = view.findViewById<TextView>(R.id.time_date)

        val user = UserList[count]
        title.text = user.title
        name.text = user.name
        content.text = user.content
        like.text = user.like.toString()
        date.text = user.date

        return view
    }

}