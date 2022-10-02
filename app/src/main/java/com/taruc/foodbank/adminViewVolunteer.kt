package com.taruc.foodbank

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class adminViewVolunteer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_view_volunteers)


        var name = findViewById<TextView>(R.id.tf_VolieName)
        var email = findViewById<TextView>(R.id.tf_VolieEmail)
        var age = findViewById<TextView>(R.id.tf_age)
        var event = findViewById<TextView>(R.id.tf_event)

        val btnBack= findViewById<Button>(R.id.btnBack)
        val db = FirebaseFirestore.getInstance()

        btnBack.setOnClickListener{
            val intent = Intent(this, AdminVolunteerActivity::class.java)
            startActivity(intent)
        }

        val Created = findViewById<TextView>(R.id.tf_VolieName)
        val bundle: Bundle? = intent.extras
        val created = bundle!!.getString("name")
        Created.text = created
        db.collection("volunteer")
            .document(created.toString())
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        name.text = document.getString("Name")
                        email.text = document.getString("Email")
                        age.text = document.getString("Age")
                        event.text = document.getString("Event")
                    } else {
                        Log.d(ContentValues.TAG, "The document doesn't exist.")
                    }
                } else {
                    task.exception?.message?.let {
                        Log.d(ContentValues.TAG, it)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }
}