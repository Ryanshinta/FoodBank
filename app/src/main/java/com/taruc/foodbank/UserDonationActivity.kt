package com.taruc.foodbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.taruc.foodbank.entity.Donation

class UserDonationActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var donationList:ArrayList<Donation>
    private lateinit var donationAdapter:DonationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_donation)

        val button = findViewById<Button>(R.id.btn_addDonation)
        button.setOnClickListener{
            val intent = Intent(this, UserNewDonationActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        donationList = arrayListOf()
        db = FirebaseFirestore.getInstance()

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
        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}