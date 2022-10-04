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
import com.taruc.foodbank.entity.volunteer
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserVolunteering : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventArrayList: ArrayList<event>
    private lateinit var tmpEventVolunteersList: ArrayList<event>
    private lateinit var volunteerArrayList: ArrayList<volunteer>
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
        volunteerArrayList = arrayListOf()
        tmpEventVolunteersList = arrayListOf()

        volunteerAdapter = VolunteerAdapter(eventArrayList)

        recyclerView.adapter = volunteerAdapter

        db = FirebaseFirestore.getInstance()

        volunteerAdapter.onItemClick = {
            val intent = Intent(this, user_Activity_Event_Detail::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }

        volunteerAdapter.onItemClick = {
            val intent = Intent(this, userFoodBankActivity::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }

        btnEvents.setOnClickListener(){
            volunteerArrayList.clear()
            eventArrayList.clear()
            Log.i("btn click","Cached document data : ")
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
        Log.i("in data function","Cached document data : ")

        val rvMyEvents = findViewById<RecyclerView>(R.id.rvMyEvents)

        db.collection("volunteer").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){
                        Log.d(ContentValues.TAG,"Cached document data : ${data?.data}")
                        val volunteerObj = data.toObject<volunteer>()
                        if (volunteerObj != null){
                            volunteerArrayList.add(volunteerObj)
                        }
                    }
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
        tmpEventVolunteersList.clear()
        db.collection("events").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){
                        if (data.get("ttlVolunteer")!=null){
                            val eventObj = data.toObject<event>()
                            if (eventObj != null){
                                eventArrayList.add(eventObj)
                                Log.d(ContentValues.TAG,"Cached document data : ${data?.data}")
                                if (eventObj.ttlVolunteer > 0){
                                    tmpEventVolunteersList.add(eventObj)
                                }
                        }else{
                                Toast.makeText(this, "No Volunteers Found", Toast.LENGTH_SHORT).show()
                            }




                        }
                    }
                    println(eventArrayList)
                    rvMyEvents.adapter = VolunteerAdapter(tmpEventVolunteersList)
                }
                //volunteerAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }


        volunteerAdapter.notifyDataSetChanged()




        /*val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
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
                        //val eventDate = eventArrayList.get(eventArrayList.size - 1).createdDate.toString()
                        if(currentDate > eventArrayList.get(eventArrayList.size - 1).dateEnd.toString() ||
                            eventArrayList.get(eventArrayList.size - 1).status == "Inactive"){
                            eventArrayList.removeAt(eventArrayList.size - 1)
                        }
                    }
                }

                for(dc : DocumentChange in value?.documentChanges!!){

                    if(dc.type == DocumentChange.Type.ADDED){
                        volunteerArrayList.add((dc.document.toObject(volunteer::class.java)))
                        val eventDate = eventArrayList.get(eventArrayList.size - 1).createdDate.toString()
                        if(eventDate > volunteerArrayList.get(volunteerArrayList.size - 1).dateEnd.toString() ||
                            volunteerArrayList.get(volunteerArrayList.size - 1).status == "Pending"){
                            volunteerArrayList.removeAt(volunteerArrayList.size - 1)
                        }
                    }
                }
                volunteerAdapter.notifyDataSetChanged()
            }
        })*/
    }
}
