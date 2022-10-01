package com.taruc.foodbank.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import java.util.*

data class event(

    var address:String?= null,
    var dateEnd: String?= null,
    var dateStart: String?= null,

    var description:String?= null,
    var image:String?= null,
    var createdDate: String?= null,

    var latitude:Double?= null,
    var longtitude:Double?= null,

    var name:String?= null,
    var status: String?= null,

    var volunteers:List<user>?= null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),

/*
        TODO("volunteers")
*/

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(dateEnd)
        parcel.writeString(dateStart)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(createdDate)
        parcel.writeDouble(latitude!!)
        parcel.writeDouble(longtitude!!)
        parcel.writeString(name)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<event> {
        override fun createFromParcel(parcel: Parcel): event {
            return event(parcel)
        }

        override fun newArray(size: Int): Array<event?> {
            return arrayOfNulls(size)
        }
    }
}
