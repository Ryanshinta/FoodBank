package com.taruc.foodbank

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.taruc.foodbank.entity.Application

class UserApplicationActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var applicationList:ArrayList<Application>
    private lateinit var applicationAdapter:ApplicationAdapter
    lateinit var mContentViewBinding: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_application)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val button = findViewById<Button>(R.id.btn_addApplication)
        button.setOnClickListener{
            val intent = Intent(this, UserNewApplicationActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        applicationList = arrayListOf()

        //get current user email
        val auth = FirebaseAuth.getInstance()
        val email = auth.currentUser?.email.toString()
        db = FirebaseFirestore.getInstance()
//        val donationRef = db.collection("donations")
//        val query = donationRef.whereEqualTo("donorEmial", donorEmail)
        db.collection("applications").whereEqualTo("applyEmail", email).get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val application: Application? = data.toObject(Application::class.java)
                    if (application != null) {
                        applicationList.add(application)
                    }
                }
                recyclerView.adapter = ApplicationAdapter(applicationList)
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}