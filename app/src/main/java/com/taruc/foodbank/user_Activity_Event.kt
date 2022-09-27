package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.taruc.foodbank.entity.event

class user_Activity_Event : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventArrayList: ArrayList<event>
    private lateinit var eventAdapter:EventAdapter
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)



        recyclerView = findViewById(R.id.rvEvent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        eventArrayList = arrayListOf()
        eventAdapter = EventAdapter(eventArrayList)
        recyclerView.adapter = eventAdapter


        setDataInList()
    }



    private fun setDataInList(){

        db = FirebaseFirestore.getInstance()
        db.collection("events").addSnapshotListener(object :EventListener<QuerySnapshot>{

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
                eventAdapter.notifyDataSetChanged()
            }
        })


    }
}