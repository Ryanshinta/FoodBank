package com.taruc.foodbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.SearchView
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class user_Activity_Event : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventArrayList: ArrayList<event>
    private lateinit var eventAdapter:EventAdapter
    /*private lateinit var tempArrayList: ArrayList<event>
    private lateinit var searchEvent: SearchView
    private lateinit var tempAdapter: ArrayAdapter<*>*/
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        /*var counter:Int = 0

        searchEvent = findViewById(R.id.searchEvent)*/

        recyclerView = findViewById(R.id.rvEvent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        eventArrayList = arrayListOf()

/*
        tempArrayList = arrayListOf<event>()
*/
        eventAdapter = EventAdapter(eventArrayList)




        recyclerView.adapter = eventAdapter


        setDataInList()

/*
        tempAdapter = ArrayAdapter<event>(this, android.R.layout.simple_list_item_1, tempArrayList)
*/

        /*searchEvent.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                for (i in 0 until tempArrayList.size) {


                    if (tempArrayList.get(i).name.equals(query)) {
                        tempAdapter.filter.filter(query)
                        counter+= 1
                    } *//*else {
                        Toast.makeText(this@MainActivity, "No Match found", Toast.LENGTH_LONG)
                            .show()
                    }*//*
                }
                eventArrayList = tempArrayList
                eventAdapter.notifyDataSetChanged()
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                tempAdapter.filter.filter(newText)
                return false
            }
        })
*/
        eventAdapter.onItemClick = {
            val intent = Intent(this, user_Activity_Event_Detail::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }
    }


    private fun setDataInList(){

        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

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
                        if(currentDate > eventArrayList.get(eventArrayList.size - 1).dateEnd.toString()){
                            eventArrayList.removeAt(eventArrayList.size - 1)
                        }
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