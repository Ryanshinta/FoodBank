package com.taruc.foodbank.entity

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import java.util.*

data class event(

    var address:String?= null,
    var dateStart: Timestamp?= null,
    var dateEnd: Timestamp?= null,
    var description:String?= null,
    var image:String?= null,
    var lastModified: Timestamp?= null,
    var location: GeoPoint?= null,
    var name:String?= null,
    var status: String?= null,
    var volunteers:List<user>?= null
)
