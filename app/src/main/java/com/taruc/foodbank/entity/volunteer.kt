package com.taruc.foodbank.entity

import android.os.Parcel
import android.os.Parcelable

data class volunteer(
//    var dateEnd: String?= null,
//    var dateStart: String?= null,
    val Name: String ?= null,
    val Age: String ?= null,
    val Email: String ?= null,
    val Status: String ?= null,
    val Event: String ?= null,
    val Contact: String ?= null
)
//): Parcelable {
//
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString()
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(dateEnd)
//        parcel.writeString(dateStart)
//        parcel.writeString(name)
//        parcel.writeString(email)
//        parcel.writeString(status)
//        parcel.writeString(event)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<event> {
//        override fun createFromParcel(parcel: Parcel): event {
//            return event(parcel)
//        }
//
//        override fun newArray(size: Int): Array<event?> {
//            return arrayOfNulls(size)
//        }
//    }
//}

