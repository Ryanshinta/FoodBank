package com.taruc.foodbank.entity

import com.google.type.DateTime

data class Donation(val donateTo: String ?= null,
//                    val donorEmail: String ?= null,
//                    val foodQty: Int ?= null,
//                    val foodType: String ?= null,
//                    val pickupAddress: String ?= null,
//                    val pickupDateTime: DateTime ?= null,
                    val status: String ?= null,
                    val food: String ?= null
)
