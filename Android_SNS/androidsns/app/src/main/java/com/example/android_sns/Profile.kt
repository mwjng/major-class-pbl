package com.example.android_sns

import android.content.ClipData
import android.content.ClipData.Item
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    fun newInstance() : Profile {
        return Profile()
    }
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var profileImage : ImageView
    val db: FirebaseFirestore = Firebase.firestore
    val email = Firebase.auth.currentUser?.email.toString()
    val storage = Firebase.storage
    val itemsCollectionRef = db.collection("users")
    val imgRef = storage.reference.child("images/${email}PROFILE")
    var imgUri = ""
    val readImg = registerForActivityResult(ActivityResultContracts.GetContent()){
        profileImage.setImageURI(it)
        imgUri = it.toString()
        val contentUri = it
        if (contentUri != null) {
            imgRef.putFile(contentUri).addOnCompleteListener{
                //Toast.makeText(context, "업로드 대따", Toast.LENGTH_SHORT).show();
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val profile = view.findViewById<Button>(R.id.profile_btn)
        val friend_btn = view.findViewById<Button>(R.id.friend_btn)
        profileImage = view.findViewById<ImageView>(R.id.profile_img)
        var UserList = arrayListOf<User>()

        itemsCollectionRef.document(email).collection("upload").get().addOnSuccessListener {
            for (doc in it) {
                UserList.add(User(doc["image"].toString().toInt(), email, doc.id, doc["title"].toString(), doc["nickname"].toString(),
                    doc["content"].toString(), doc["like"].toString().toInt(), doc["date"].toString()))
            }
            UserList.sortByDescending { it.date }
            var Adapter = ListAdapter(context, UserList)
            view.findViewById<ListView>(R.id.list2).adapter = Adapter
        }
        /*storage.reference.child("images/${email}PROFILE").downloadUrl.addOnSuccessListener {
            profileImage.setImageURI(it)
        }*/

        imgRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            profileImage.setImageBitmap(bmp)
        }

        queryItem(email, view)

        profileImage.setOnClickListener {
            // 프로필 사진 클릭
            //navigatePhotos()
            readImg.launch("image/*")
            //itemsCollectionRef.document(email).update("image", readImg) )
        }
        profile.setOnClickListener {
            startActivity(Intent(activity, ProfileSetting::class.java))
        }
        friend_btn.setOnClickListener {
            // 친구 추가 버튼
            startActivity(Intent(activity,FriendAdd::class.java))
        }
        return view
    }

    private fun queryItem(itemID: String, view: View) {
        itemsCollectionRef.document(itemID).get()
            .addOnSuccessListener {
                view.findViewById<TextView>(R.id.profile_name).setText(it["nickname"].toString())
                view.findViewById<TextView>(R.id.profile_msg).setText(it["message"].toString())
            }.addOnFailureListener {
            }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun navigatePhotos(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "images/*"
        //val permissonCheck = requireActivity().checkSelfPermission();
    }
}