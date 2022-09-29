package com.taruc.foodbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.taruc.foodbank.entity.event
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class admin_Activity_Event : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventArrayList: ArrayList<event>
    private lateinit var eventAdapter:EventAdapter
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_event)

        val btnAddEvent = findViewById<Button>(R.id.btnAddEvent)

        recyclerView = findViewById(R.id.rvAdminEvent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        eventArrayList = arrayListOf()

        eventAdapter = EventAdapter(eventArrayList)
        recyclerView.adapter = eventAdapter


        setDataInList()

        eventAdapter.onItemClick = {
            val intent = Intent(this, admin_Activity_Event_Detail::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }

        btnAddEvent.setOnClickListener(){
            val intent = Intent(this, admin_Activity_New_Event::class.java)
            startActivity(intent)
        }
    }

    private fun setDataInList(){

        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        db = FirebaseFirestore.getInstance()
        db.collection("events").addSnapshotListener(object : EventListener<QuerySnapshot> {

            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ){
                if(error != null){
                    Log.e("Firestore Error", error.message.toString())
                    return

                }

                for(dc : DocumentChange in value?.documentChanges!!){

                    if(dc.type == DocumentChange.Type.ADDED){
                        eventArrayList.add((dc.document.toObject(event::class.java)))

                    }
                }
/*
                tempArrayList.addAll(eventArrayList)
*/
                eventAdapter.notifyDataSetChanged()
            }
        })


    }
}