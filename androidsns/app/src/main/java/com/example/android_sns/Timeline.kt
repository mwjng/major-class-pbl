package com.example.android_sns

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Timeline.newInstance] factory method to
 * create an instance of this fragment.
 */
class Timeline : Fragment() {
    fun newInstance() : Timeline {
        return Timeline()
    }
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val db: FirebaseFirestore = Firebase.firestore
    val email = Firebase.auth.currentUser?.email.toString()
    val itemsCollectionRef = db.collection("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timeline, container, false)

        var UserList = arrayListOf<User>()


        var boo = true
        itemsCollectionRef.document(email).get()
            .addOnSuccessListener {
                boo = it["friend"].toString().toBoolean()
            }.addOnFailureListener{}

        itemsCollectionRef.document(email).collection("upload").get()
            .addOnSuccessListener {
                for (doc in it) {
                    UserList.add(
                        User(
                            doc["image"].toString().toInt(),
                            doc["writer"].toString(),
                            doc.id,
                            doc["title"].toString(),
                            doc["nickname"].toString(),
                            doc["content"].toString(),
                            doc["like"].toString().toInt(),
                            doc["date"].toString()
                        )
                    )
                }
                UserList.sortByDescending { it.date }
                var Adapter = ListAdapter(context, UserList)
                view.findViewById<ListView>(R.id.listview).adapter = Adapter


                if (boo) {
                    itemsCollectionRef.document(email).collection("friends").get().addOnSuccessListener {
                        for (doc1 in it) {
                            itemsCollectionRef.document(doc1.id).collection("upload").get()
                                .addOnSuccessListener {
                                    for (doc in it) {
                                        UserList.add(
                                            User(
                                                doc["image"].toString().toInt(),
                                                doc["writer"].toString(),
                                                doc.id,
                                                doc["title"].toString(),
                                                doc["nickname"].toString(),
                                                doc["content"].toString(),
                                                doc["like"].toString().toInt(),
                                                doc["date"].toString()
                                            )
                                        )
                                    }
                                    UserList.sortByDescending { it.date }
                                    var Adapter = ListAdapter(context, UserList)
                                    view.findViewById<ListView>(R.id.listview).adapter = Adapter
                                }
                        }
                    }
                }
            }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Timeline.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Timeline().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}