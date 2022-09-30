package com.taruc.foodbank

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class admin_Activity_New_Event : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_new_event)


        val btnStartDate = findViewById<Button>(R.id.btnStartDate)
        val btnStartTime = findViewById<Button>(R.id.btnStartTime)
        val btnEndDate = findViewById<Button>(R.id.btnEndDate)
        val btnEndTime = findViewById<Button>(R.id.btnEndTime)




    }

    /*fun selectDate (){
*//*
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->  })
*//*
    }*/



}