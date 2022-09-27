package com.taruc.foodbank

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import java.util.*
import kotlin.math.min

class UserNewDonationActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var selectedDay = 0
    var selectedMonth = 0
    var selectedYear = 0
    var selectedHour = 0
    var selectedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_new_donation)

        val buttonChoose = findViewById<Button>(R.id.btn_choose)

        buttonChoose.setOnClickListener{
            getDateTimeCalender()

            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    private fun getDateTimeCalender(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        selectedDay = dayOfMonth
        selectedMonth = month
        selectedYear = year

        getDateTimeCalender()
        TimePickerDialog(this,this,hour, minute,true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        selectedHour = hourOfDay
        selectedMinute = minute

        val choosedDateTime = findViewById<TextView>(R.id.tv_pickedDateTime)
        choosedDateTime.text = "$selectedDay/$selectedMonth/$selectedYear  $selectedHour:$selectedMinute"
    }
}