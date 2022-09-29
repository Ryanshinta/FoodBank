package com.taruc.foodbank

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.google.common.collect.Maps
import com.google.firebase.firestore.FirebaseFirestore

class UserDonationDetailsActivity : AppCompatActivity() {
//    lateinit var donation : ArrayList<>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_details)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val Created: TextView = findViewById(R.id.tf_dateTimeCreated)
        val bundle: Bundle? = intent.extras
        val created = bundle!!.getString("dateTimeCreated")
        Created.text = created

        val foodbank = findViewById<TextView>(R.id.tf_DonateTo)
        val email = findViewById<TextView>(R.id.tf_donorEmail)
        val type = findViewById<TextView>(R.id.tf_foodType)
        val fooditem = findViewById<TextView>(R.id.tf_foodItem)
        val quantity = findViewById<TextView>(R.id.tf_foodQty)
        val address = findViewById<TextView>(R.id.tf_pickupLocation)
        val datetime = findViewById<TextView>(R.id.tf_pickedDateTime)
        val status = findViewById<TextView>(R.id.tf_status)

        val db = FirebaseFirestore.getInstance()
//        donation = arrayListOf()
        db.collection("donations")
            .document(created.toString())
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        foodbank.text = document.getString("donateTo")
                        email.text = document.getString("donorEmail")
                        type.text = document.getString("foodType")
                        fooditem.text = document.getString("food")
                        quantity.text = document.getLong("foodQty").toString()
                        address.text = document.getString("pickupAddress")
                        datetime.text = document.getString("pickupDateTime")
                        status.text = document.getString("status")
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