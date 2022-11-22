package com.example.android_sns

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListAdapter(val context: Context?, val email: String) : BaseAdapter() {
    val db: FirebaseFirestore = Firebase.firestore
    val itemsCollectionRef = db.collection("users")

    override fun getCount(): Int {
        var count = 0
        itemsCollectionRef.document(email).get()
            .addOnSuccessListener {
                count = it["uploadCount"].toString().toInt()
            }.addOnFailureListener {
            }
        return count
    }
    override fun getItem(i: Int): Int {
        return 0
    }
    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(count: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(parent?.context).inflate(R.layout.card, null)
        val title = view.findViewById<TextView>(R.id.time_title)
        val name = view.findViewById<TextView>(R.id.time_name)
        val content = view.findViewById<TextView>(R.id.time_content)
        val like = view.findViewById<TextView>(R.id.time_count)
        val date = view.findViewById<TextView>(R.id.time_date)

        itemsCollectionRef.document(email).collection("upload").document(count.toString()).get()
            .addOnSuccessListener {
                title.text = it["title"].toString()
                name.text = it["nickname"].toString()
                content.text = it["content"].toString()
                like.text = it["like"].toString()
                date.text = it["date"].toString()
            }.addOnFailureListener {
            }

        return view
    }

}