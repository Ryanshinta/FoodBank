package com.taruc.foodbank.entity

data class user(
    //val id:String,
    val name:String = "",
    val email:String = "",
    //val password:String,
    val role:role = com.taruc.foodbank.entity.role.USER
)
