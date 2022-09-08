package com.taruc.foodbank.entity

data class user(
    val id:Long,
    val name:String,
    val email:String,
    val password:String,
    val role:role
)
