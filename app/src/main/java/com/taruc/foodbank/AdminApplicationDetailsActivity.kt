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

class AdminApplicationDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_application_details)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.admin)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3640FF")))

        val Created: TextView = findViewById(R.id.tf_dateTimeApplied)
        val bundle: Bundle? = intent.extras
        val created = bundle!!.getString("dateTimeApplied")
        Created.text = created

        var resources = findViewById<TextView>(R.id.tf_resources)
        var amount = findViewById<TextView>(R.id.tf_amount)
        var description = findViewById<TextView>(R.id.tf_description)
        var emergencyLevel = findViewById<TextView>(R.id.tf_emergencyLevel)
        var status = findViewById<TextView>(R.id.tf_applicationStatus)
        var applyEmail = findViewById<TextView>(R.id.tf_applyEmail)
        var name = findViewById<TextView>(R.id.tf_applyBy)

        val backButton = findViewById<Button>(R.id.btn_back)
        val rejectButton = findViewById<Button>(R.id.btn_rejected)
        val approveButton = findViewById<Button>(R.id.btn_approved)
        val emailButton = findViewById<Button>(R.id.btn_email)
        val db = FirebaseFirestore.getInstance()

        emailButton.setOnClickListener{
            val intent = Intent(this, SendEmailApplicationActivity::class.java)
                .putExtra("email",applyEmail.text.toString())
            startActivity(intent)
        }

        rejectButton.setOnClickListener{
            db.collection("applications").document(created.toString()).update("applicationStatus", "Rejected")
            status.text = "Rejected"
        }

        approveButton.setOnClickListener{
            db.collection("applications").document(created.toString()).update("applicationStatus", "Approved")
            status.text = "Approved"
        }

        backButton.setOnClickListener{
            val intent = Intent(this, AdminApplicationActivity::class.java)
            startActivity(intent)
        }
//        donation = arrayListOf()
        db.collection("applications")
            .document(created.toString())
            .get()
            .addOnCompleteListener{ task ->
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