package com.taruc.foodbank.entity

import android.widget.TextView
import com.google.type.DateTime

data class Donation(
    val donateTo: String ?= null,
//    val donorEmail: String ?= null,
//    val foodQty: Int ?= null,
//    val foodType: String ?= null,
//    val pickupAddress: String ?= null,
//    val pickupDateTime: TextView? = null,
    val status: String ?= null,
    val food: String ?= null
)
