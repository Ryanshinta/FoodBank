package com.taruc.foodbank.entity

import java.util.*

data class food(
    val name:String,
    val amount:Int,
    val expireDate: Date,
    val donor:user,
    val description:String
)
