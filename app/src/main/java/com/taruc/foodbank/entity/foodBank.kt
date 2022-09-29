package com.taruc.foodbank.entity

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint


data class foodBank(
    val name:String = "",
    //val image:String,
    val address:String = "",
    val contactNumber: String = "",
    val foods:Map<String,Int> ?= null,
    val volunteers: ArrayList<user> ?= null,
    val description:String = "",
    //val location: GeoPoint ?= null
    val lat:Double = 0.0,
    val lng:Double = 0.0
)
