package com.taruc.foodbank.entity

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint


data class foodBank(
    val name:String = "",
    //val image:String,
    val address:String = "",
    val foods:List<food> ?= null,
    val volunteers: ArrayList<user> ?= null,
    val description:String = "",
    val location: GeoPoint ?= null
)
