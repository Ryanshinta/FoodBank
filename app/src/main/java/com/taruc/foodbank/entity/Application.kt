package com.taruc.foodbank.entity

data class Application(
    val applyBy : String ?= null,
    val resourcesName : String ?= null,
    val resourcesAmount : Int ?= null,
    val description : String ?= null,
    val emergencyLevel : String ?= null,
    val dateTimeApplied : String ?= null,
    val applicationStatus : String ?= null
)
