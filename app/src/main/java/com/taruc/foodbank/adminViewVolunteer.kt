package com.taruc.foodbank

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.taruc.foodbank.entity.volunteer
import java.util.*

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

        //val Created = findViewById<TextView>(R.id.tf_VolieName)
        val created = intent.getStringExtra("name")
        Log.i("GetIntentData",created.toString())
        db.collection("volunteer")
            .whereEqualTo("Name",created.toString())
            .get()
            .addOnSuccessListener {
             for (task in it.documents)
                if (task.exists()) {
                    val document = task.toObject<volunteer>()

                    if (Objects.nonNull(document)) {
                        name.text = document!!.Name
                        Log.i("GetData",name.text.toString())
                        email.text = document!!.Email
                        age.text = document!!.Age
                        event.text = document!!.Event
                    } else {
                        Log.d(ContentValues.TAG, "The document doesn't exist.")
                    }
                }
                }
            .addOnFailureListener{
                Log.d("Failure", it.toString())
            }
            }

    }
