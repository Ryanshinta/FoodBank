package com.taruc.foodbank

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.taruc.foodbank.databinding.ActivityAdminBinding
import com.taruc.foodbank.databinding.ActivityUserFoodBankBinding
import com.taruc.foodbank.entity.foodBank

class admin_activity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var foodBankList:ArrayList<foodBank>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_admin)

        binding.rvFoodBank.layoutManager = LinearLayoutManager(this)
        binding.rvFoodBank.setHasFixedSize(true)
        //setActionBar(findViewById(R.id.toolbar2))
        db = FirebaseFirestore.getInstance()
        foodBankList = arrayListOf()

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
                    binding.rvFoodBank.adapter = FoodBankRecyclerViewAdapter(foodBankList)
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }

        binding.btNew.setOnClickListener(){
            Log.i("click","bt clicked")
            val intent = Intent(this, AdminNewFoodBankActivity::class.java)
            startActivity(intent)
        }


    }

}