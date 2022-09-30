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
import com.google.firebase.firestore.*
import com.taruc.foodbank.entity.Application

class AdminApplicationActivity : AppCompatActivity() {
    lateinit var statusType:ArrayList<String>
    private lateinit var db:FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var applicationList:ArrayList<Application>
    private lateinit var donationAdapter:ApplicationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_application)

        statusType = arrayListOf()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.admin)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3640FF")))
        val button = findViewById<Button>(R.id.btn_back)
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val spinner: Spinner = findViewById<Spinner>(R.id.sp_statusType)
        var applicationStatus = findViewById<Spinner>(R.id.sp_statusType)
        statusType = arrayListOf("--Select a Type--", "Pending", "Approved", "Rejected", "Canceled")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, statusType)
        spinner.adapter = adapter
        spinner.setSelection(0)
        val statusValue = applicationStatus.selectedItem.toString()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        applicationList = arrayListOf()
        db = FirebaseFirestore.getInstance()
        val totalApplication = findViewById<TextView>(R.id.tvDescription)
        //get current user email
        val auth = FirebaseAuth.getInstance()

//        val donationRef = db.collection("donations")
//        val query = donationRef.whereEqualTo("donorEmial", donorEmail)

        if (statusValue == "--Select a Type--") {
            db.collection("applications").get().addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val application: Application? = data.toObject(Application::class.java)
                        if (application != null) {
                            applicationList.add(application)
                        }
                    }
                    recyclerView.adapter = ApplicationAdapter(applicationList)
                }
                totalApplication.text =
                    "You received ${applicationList.size.toString()} application this year."
            }
                .addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
        val filterButton = findViewById<Button>(R.id.btn_filter)

        filterButton.setOnClickListener {
            val intent = Intent(this, FilterApplicationActivity::class.java)
                .putExtra("statusValue", applicationStatus.selectedItem.toString())
            startActivity(intent)
        }
    }
}