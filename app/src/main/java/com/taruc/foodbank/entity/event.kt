package com.taruc.foodbank.entity

import java.util.*

data class event(
    val startTime: Date,
    val endTime: Date,
    val name:String,
    val description:String,
    val volunteers:List<user>
)
