package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.taruc.foodbank.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val userDb = db.collection("users").document("1w0yaqjKFBpqwKt8CG7T")
        userDb.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }

        user?.let {
            //val name = user.name
            val uid = user.uid
            val email = user.email
            val emailVerified = user.isEmailVerified


            //binding.tvShowName.text = name.toString()
            binding.tvShowEmail.text = email.toString()

        }
    }
}