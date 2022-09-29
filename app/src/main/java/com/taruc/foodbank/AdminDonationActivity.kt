package com.taruc.foodbank

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.taruc.foodbank.entity.Donation

class AdminDonationActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var donationList:ArrayList<Donation>
    private lateinit var donationAdapter:DonationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_donation)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.admin)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3640FF")))
        val button = findViewById<Button>(R.id.btn_addDonation)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        donationList = arrayListOf()
        db = FirebaseFirestore.getInstance()
        val totalDonation = findViewById<TextView>(R.id.tvDescription)
        //get current user email
        val auth = FirebaseAuth.getInstance()

//        val donationRef = db.collection("donations")
//        val query = donationRef.whereEqualTo("donorEmial", donorEmail)
        db.collection("donations").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val donation: Donation? = data.toObject(Donation::class.java)
                    if (donation != null) {
                        donationList.add(donation)
                    }
                }
                recyclerView.adapter = DonationAdapter(donationList)
            }
            totalDonation.text = "You have ${donationList.size.toString()} donation this year."
        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}