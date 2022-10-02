package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taruc.foodbank.entity.volunteer

class AdminVolunteerActivity : AppCompatActivity() {
    lateinit var statusType:ArrayList<String>
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var volunteerList:ArrayList<volunteer>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_all_volunteer)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        volunteerList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        val auth = FirebaseAuth.getInstance()

        db.collection("volunteer").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val volunteer: volunteer? = data.toObject(volunteer::class.java)
                    if (volunteer != null) {
                        volunteerList.add(volunteer)
                    }
                }
                recyclerView.adapter = VolunteerListAdapter(volunteerList)
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}