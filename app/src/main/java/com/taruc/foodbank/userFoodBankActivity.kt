//package com.taruc.foodbank
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//
//import com.taruc.foodbank.databinding.ActivityUserFoodBankBinding
//import com.taruc.foodbank.entity.food
//import com.taruc.foodbank.entity.foodBank
//import com.taruc.foodbank.entity.role
//import com.taruc.foodbank.entity.user
//import java.util.*
//
//class userFoodBankActivity: AppCompatActivity()  {
//    private lateinit var binding: ActivityUserFoodBankBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val initFoodBank = mutableListOf<foodBank>(
//            foodBank("Food bank001","Address 001",
//                foods = listOf(
//                    food("food001",1, Date(),
//                        user("ryan","ryan@gmail.com", role.USER),"food1")
//                ),null,"bank001"),
//
//            foodBank("Food bank002","Address 001",
//                foods = listOf(
//                    food("food001",1, Date(),
//                        user("ryan","ryan@gmail.com", role.USER),"food1")
//                ),null,"bank001"),
//
//            foodBank("Food bank003","Address 001",
//                foods = listOf(
//                    food("food001",1, Date(),
//                        user("ryan","ryan@gmail.com", role.USER),"food1")
//                ),null,"bank001"),
//        )
//
//        setContentView(R.layout.activity_user_food_bank)
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_food_bank)
//
//        binding.rvFoodBank.adapter = FoodBackRecyclerViewAdapter(initFoodBank)
//    }
//}