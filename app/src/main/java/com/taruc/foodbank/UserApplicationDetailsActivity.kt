package com.taruc.foodbank

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.google.common.collect.Maps
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserApplicationDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_application_details)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val Created: TextView = findViewById(R.id.tf_dateTimeApplied)
        val bundle: Bundle? = intent.extras
        val created = bundle!!.getString("dateTimeApplied")
        Created.text = created
        val auth = FirebaseAuth.getInstance()

        var resources = findViewById<TextView>(R.id.tf_resources)
        var amount = findViewById<TextView>(R.id.tf_amount)
        var description = findViewById<TextView>(R.id.tf_description)
        var emergencyLevel = findViewById<TextView>(R.id.tf_emergencyLevel)
        var status = findViewById<TextView>(R.id.tf_applicationStatus)
        var applyEmail = findViewById<TextView>(R.id.tf_applyEmail)
        var name = findViewById<TextView>(R.id.tf_applyBy)

        val backButton = findViewById<Button>(R.id.btn_back)
        val cancelButton = findViewById<Button>(R.id.btn_cancelApplication)
        val db = FirebaseFirestore.getInstance()

        cancelButton.setOnClickListener{
            db.collection("applications").document(created.toString()).update("applicationStatus", "Canceled")
            status.text = "Canceled"
        }

        backButton.setOnClickListener{
            val intent = Intent(this, UserApplicationActivity::class.java)
            startActivity(intent)
        }

//        donation = arrayListOf()
        db.collection("applications")
            .document(created.toString())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        resources.text = document.getString("resourcesName")
                        amount.text = document.getLong("resourcesAmount").toString()
                        description.text = document.getString("description")
                        emergencyLevel.text = document.getString("emergencyLevel")
                        applyEmail.text = document.getString("applyEmail")
                        name.text = document.getString("applyBy")
                        status.text = document.getString("applicationStatus")
                    } else {
                        Log.d(TAG, "The document doesn't exist.")
                    }
                } else {
                    task.exception?.message?.let {
                        Log.d(TAG, it)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}