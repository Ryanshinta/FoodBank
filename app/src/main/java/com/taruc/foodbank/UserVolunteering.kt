package com.taruc.foodbank

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.toObject
import com.taruc.foodbank.entity.event
import com.taruc.foodbank.entity.foodBank
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserVolunteering : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventArrayList: ArrayList<event>
    private lateinit var foodBankList: ArrayList<foodBank>
    private lateinit var volunteerAdapter:VolunteerAdapter
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_volunteering)

        val rvMyEvents = findViewById<RecyclerView>(R.id.rvMyEvents)

        val btnEvents = findViewById<Button>(R.id.btnEvent)
        val btnFB = findViewById<Button>(R.id.btnFB)

        recyclerView = findViewById(R.id.rvMyEvents)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        eventArrayList = arrayListOf()
        foodBankList = arrayListOf()

        volunteerAdapter = VolunteerAdapter(eventArrayList)

        recyclerView.adapter = volunteerAdapter

        db = FirebaseFirestore.getInstance()
        eventArrayList = arrayListOf()
        foodBankList = arrayListOf()


        volunteerAdapter.onItemClick = {
            val intent = Intent(this, user_Activity_Event_Detail::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }

        volunteerAdapter.onItemClick = {
            val intent = Intent(this, admin_Activity_FoodBank::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }

        btnEvents.setOnClickListener(){
            db.collection("events").get()
                .addOnSuccessListener {
                    if (!it.isEmpty){
                        for (data in it.documents){
                            Log.d(ContentValues.TAG,"Cached document data : ${data?.data}")
                            val eventObj = data.toObject<event>()
                            if (eventObj != null){
                                eventArrayList.add(eventObj)
                            }
                        }
                        rvMyEvents.adapter = VolunteerAdapter(eventArrayList)
                    }
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }

            setDataInList()
        }

        btnFB.setOnClickListener(){
            db.collection("foodbanks").get()
                .addOnSuccessListener {
                    if (!it.isEmpty){
                        for (data in it.documents){
                            Log.d(ContentValues.TAG,"Cached document data : ${data?.data}")
                            val foodBankObj = data.toObject<foodBank>()
                            if (foodBankObj != null){
                                foodBankList.add(foodBankObj)
                            }
                        }
                        rvMyEvents.adapter = FoodBankRecyclerViewAdapter(foodBankList)
                    }
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
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
                        if(currentDate > eventArrayList.get(eventArrayList.size - 1).dateEnd.toString() ||
                            eventArrayList.get(eventArrayList.size - 1).status == "Registered"){
                            eventArrayList.removeAt(eventArrayList.size - 1)
                        }
                    }
                }
            }
        })
    }
}
