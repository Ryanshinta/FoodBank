package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.taruc.foodbank.entity.foodBank
import com.taruc.foodbank.entity.history
import org.w3c.dom.Text

class FoodBankHistory : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var historyList:ArrayList<history>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_bank_history)
        val foodBankName: String? = intent.getStringExtra("FoodBankName")

        val tvTitle = findViewById<TextView>(R.id.tvFoodBankName2)
        val recyclerList = findViewById<RecyclerView>(R.id.rvHistory)
        val tvNull = findViewById<TextView>(R.id.tvNull)
        val srLayout = findViewById<SwipeRefreshLayout>(R.id.srLayout)
        tvTitle.text = foodBankName.toString()

        srLayout.setOnRefreshListener {
            srLayout.isRefreshing = false
            reloadData()
        }

        db = FirebaseFirestore.getInstance()
        historyList = arrayListOf()

        db.collection("foodHistory").whereEqualTo("foodbankName",foodBankName.toString()).get()
            .addOnSuccessListener {
                for (data in it.documents){
                    Log.i("Data",data.data.toString())
                    var historyObj = data.toObject<history>()

                    if (historyObj != null){
                        historyList.add(historyObj)
                    }
                }
                if (historyList.isEmpty()){
                    recyclerList.visibility = View.GONE
                    tvNull.visibility = View.VISIBLE
                }else{
                    Log.i("Data",historyList.toString())
                    recyclerList.layoutManager = LinearLayoutManager(this)
                    recyclerList.setHasFixedSize(true)
                    recyclerList.adapter = HistoryAdapter(historyList)
//                    recyclerList.visibility = View.GONE
//                    tvNull.visibility = View.VISIBLE
                }

            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }

    }

    private fun reloadData() {
        val foodBankName: String? = intent.getStringExtra("FoodBankName")
        val recyclerList = findViewById<RecyclerView>(R.id.rvHistory)
        val tvNull = findViewById<TextView>(R.id.tvNull)
        db = FirebaseFirestore.getInstance()
        historyList = arrayListOf()

        db.collection("foodHistory").whereEqualTo("foodbankName",foodBankName.toString()).get()
            .addOnSuccessListener {
                for (data in it.documents){
                    Log.i("Data",data.data.toString())
                    var historyObj = data.toObject<history>()

                    if (historyObj != null){
                        historyList.add(historyObj)
                    }
                }
                if (historyList.isEmpty()){
                    recyclerList.visibility = View.GONE
                    tvNull.visibility = View.VISIBLE
                }else{
                    Log.i("Data at Activity",historyList.toString())
                    recyclerList.layoutManager = LinearLayoutManager(this)
                    recyclerList.setHasFixedSize(true)
                    recyclerList.adapter = HistoryAdapter(historyList)
//                    recyclerList.visibility = View.GONE
//                    tvNull.visibility = View.VISIBLE
                }

            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}