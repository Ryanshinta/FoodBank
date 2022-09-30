package com.taruc.foodbank

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taruc.foodbank.entity.Donation

class FilterDonationActivity : AppCompatActivity() {
    lateinit var statusType:ArrayList<String>
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var donationList:ArrayList<Donation>
    private lateinit var donationAdapter:DonationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_donation)

        val status = intent.getStringExtra("statusValue")
        var filterStatus = ""

        if (status.toString() == "Pending"){
            filterStatus = "Pending"
        }else if (status.toString() == "Canceled"){
            filterStatus = "Canceled"
        }else if (status.toString() == "Completed"){
            filterStatus = "Completed"
        }else if (status.toString() == "Failed"){
            filterStatus = "Failed"
        }

        statusType = arrayListOf()
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

        val spinner: Spinner = findViewById<Spinner>(R.id.sp_statusType)
        var donationStatus = findViewById<Spinner>(R.id.sp_statusType)
        statusType = arrayListOf("--Select a Type--", "Pending", "Failed", "Canceled", "Completed")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, statusType)
        spinner.adapter = adapter
        spinner.setSelection(0)
        val statusValue = donationStatus.selectedItem.toString()

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

        if (statusValue == "--Select a Type--"){
            db.collection("donations").whereEqualTo("status", filterStatus).get().addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val donation: Donation? = data.toObject(Donation::class.java)
                        if (donation != null) {
                            donationList.add(donation)
                        }
                    }
                    recyclerView.adapter = DonationAdapter(donationList)
                }
                totalDonation.text = "There are ${donationList.size.toString()} $status Donation."
            }
                .addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
        val filterButton = findViewById<Button>(R.id.btn_filter)

        filterButton.setOnClickListener {
            val intent = Intent(this, FilterDonationActivity::class.java)
                .putExtra("statusValue", donationStatus.selectedItem.toString())
            startActivity(intent)
        }
    }
}