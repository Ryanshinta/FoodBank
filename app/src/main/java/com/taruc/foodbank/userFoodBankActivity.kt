package com.taruc.foodbank

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

import com.taruc.foodbank.databinding.ActivityUserFoodBankBinding

import com.taruc.foodbank.entity.foodBank

import java.util.*
import kotlin.collections.ArrayList

class userFoodBankActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityUserFoodBankBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var foodBankList:ArrayList<foodBank>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_food_bank)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_food_bank)

        binding.rvFoodBank.layoutManager = LinearLayoutManager(this)
        binding.rvFoodBank.setHasFixedSize(true)

        db = FirebaseFirestore.getInstance()
        foodBankList = arrayListOf()

        db.collection("foodbanks").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){
                        Log.d(TAG,"Cached document data : ${data?.data}")
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



    }

//    private fun filter(text:String){
//        val filteredList = ArrayList<foodBank>()
//
//        for (item in foodBankList){
//            if (item.name.lowercase(Locale.ROOT).contains(text.lowercase())){
//                filteredList.add(item)
//            }
//        }
//        if (filteredList.isEmpty()){
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
//        }else{
//            binding.rvFoodBank.adapter = FoodBankRecyclerViewAdapter(filteredList)
//        }
//    }
}