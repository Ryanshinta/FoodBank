package com.taruc.foodbank

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class AdminDonationDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_donation_details)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.admin)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3640FF")))

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

        val backButton = findViewById<Button>(R.id.btn_back)
        val cancelButton = findViewById<Button>(R.id.btn_cancelDonation)
        val completeButton = findViewById<Button>(R.id.btn_completeDonation)
        val db = FirebaseFirestore.getInstance()

        val emailButton = findViewById<Button>(R.id.btn_email)

        emailButton.setOnClickListener{
            val intent = Intent(this, SendEmailDonationActivity::class.java)
                .putExtra("email",email.text.toString())
            startActivity(intent)
        }

        completeButton.setOnClickListener{
            db.collection("donations").document(created.toString()).update("status", "Completed")
            val foodbankDB = db.collection("foodbanks")
            foodbankDB.whereEqualTo("name", foodbank.text).get().addOnSuccessListener {
                for (document in it.documents){
                    val fbId = document.id
                    val map = mapOf("foods.${fooditem.text}" to quantity.text.toString().toInt())
                    db.collection("foodbanks").document(fbId).update(map)
                }
            }
            status.text = "Completed"
        }

        cancelButton.setOnClickListener{
            db.collection("donations").document(created.toString()).update("status", "Failed")
            status.text = "Failed"
        }

        backButton.setOnClickListener{
            val intent = Intent(this, AdminDonationActivity::class.java)
            startActivity(intent)
        }
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