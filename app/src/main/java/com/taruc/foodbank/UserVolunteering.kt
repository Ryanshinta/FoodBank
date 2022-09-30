package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.taruc.foodbank.EventAdapter
import com.taruc.foodbank.R
import com.taruc.foodbank.entity.event

class UserVolunteering : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventArrayList: ArrayList<event>
    private lateinit var eventAdapter: EventAdapter
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_volunteering)

        recyclerView = findViewById(R.id.rvMyEvents)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        eventArrayList = arrayListOf()

        eventAdapter = EventAdapter(eventArrayList)

        recyclerView.adapter = eventAdapter

        //setDataInList()
    }
}