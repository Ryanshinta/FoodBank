package com.taruc.foodbank

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

import com.taruc.foodbank.databinding.ActivityUserFoodBankBinding

import com.taruc.foodbank.entity.foodBank

import java.util.*

class userFoodBankActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityUserFoodBankBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var foodBankList:ArrayList<foodBank>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_food_bank)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_food_bank)

        db = FirebaseFirestore.getInstance()

        db.collection("foodbanks").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){
                        val foodBankObj:foodBank? = data.toObject(foodBank::class.java)
                        if (foodBankObj != null){
                            foodBankList.add(foodBankObj)
                        }
                    }
                    binding.rvFoodBank.adapter = FoodBackRecyclerViewAdapter(foodBankList)
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }



    }
}