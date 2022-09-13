package com.taruc.foodbank.entity



data class foodBank(
    val name:String,
    val address:String,
    val foods:List<food>,
    val volunteers: List<user>?,
    val description:String
)
