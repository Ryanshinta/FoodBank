package com.taruc.foodbank

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import java.io.IOException
import java.util.*

class admin_Activity_New_Event : AppCompatActivity() , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0
    var datetime: String = "DATE"
    var startNum:String = ""
    var endNum:String = ""
    var num = 0

    var selectedDay = ""
    var selectedMonth = ""
    var selectedYear = 0
    var selectedHour = ""
    var selectedMinute = ""
    private lateinit var imgUri: Uri

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_new_event)


        val btnStartDate = findViewById<Button>(R.id.btnStartDate)
        val btnEndDate = findViewById<Button>(R.id.btnEndDate)
        val btnUploadImage = findViewById<Button>(R.id.btnEventImage)
        val btnAddNewEvent = findViewById<Button>(R.id.btnAddNewEvent)
        val tfAddress = findViewById<TextView>(R.id.tfEventAddress)
        val tfEventName = findViewById<TextView>(R.id.tfEventName)
        val tfEventDesciption = findViewById<TextView>(R.id.tfEventDescription)
        val imgEvent = findViewById<ImageView>(R.id.imgNewEvent)



        btnStartDate.setOnClickListener(){

            getDateTimeCalender()

            num = 1

            DatePickerDialog(this, this, year, month, day).show()

        }

        btnEndDate.setOnClickListener(){
            getDateTimeCalender()

            num = 2
            DatePickerDialog(this, this, year, month, day).show()

        }

        btnUploadImage.setOnClickListener(){
            getImage.launch("image/*")
        }

        btnAddNewEvent.setOnClickListener(){
            if(tfEventName.text.trim().isEmpty()) {
                Toast.makeText(applicationContext, "Please Enter Event Name", Toast.LENGTH_SHORT).show()
            }else if(tfEventDesciption.text.trim().isEmpty()){
                Toast.makeText(applicationContext, "Please Enter Event Description", Toast.LENGTH_SHORT).show()
            }else if(btnStartDate.text == "DATE"){
                Toast.makeText(applicationContext, "Please select Start Date", Toast.LENGTH_SHORT).show()
            }
            else if(btnEndDate.text == "DATE"){
                Toast.makeText(applicationContext, "Please select End Date", Toast.LENGTH_SHORT).show()
            }
            else if(tfAddress.text.trim().isEmpty()){

                Toast.makeText(applicationContext, "Please Enter Event Address", Toast.LENGTH_SHORT).show()
                /*var arrayLocation: DoubleArray =getLocationFromAddress(tfAddress.text.toString())
                if(arrayLocation[0] == 0.0){
                    Toast.makeText(applicationContext, "Please Enter Valid Address", Toast.LENGTH_SHORT).show()
                }*/
            }
            else if(imgEvent.drawable == null){
                Toast.makeText(applicationContext, "Please Enter Event Image", Toast.LENGTH_SHORT).show()
            }else{

            }

        }


    }

    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri ->

        val btnUploadImage = findViewById<Button>(R.id.btnEventImage)
        imgUri = uri!!

        val imgNewEvent = findViewById<ImageView>(R.id.imgNewEvent)
        imgNewEvent.setImageURI(uri)
    }

    private fun getLocationFromAddress(strAddress: String?): DoubleArray{
        val coder = Geocoder(this)
        var address: List<Address>?
        val btnAddNewEvent = findViewById<Button>(R.id.btnAddNewEvent)
        var arrayLocation = DoubleArray(2) { 0.0 }

            address = coder.getFromLocationName(strAddress, 5)
            if (address.isNotEmpty()) {
                btnAddNewEvent.text = "knnb"

                val location = address[0]
                arrayLocation[0] = location.latitude.toDouble()
                arrayLocation[1] = location.longitude.toDouble()
            }

        return arrayLocation
    }

    fun setDateText (num:Int){
        val btnStartDate = findViewById<Button>(R.id.btnStartDate)
        val btnEndDate = findViewById<Button>(R.id.btnEndDate)

        if(num == 1){
            startNum = datetime
            btnStartDate.text = datetime

        }else{
            endNum = datetime
            btnEndDate.text = datetime
        }
        datetime ="Date"
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
        selectedDay = dayOfMonth.toString()
        selectedMonth = month.toString()
        selectedYear = year

        getDateTimeCalender()
        if(selectedMonth.length == 1){
            selectedMonth = "0${selectedMonth.toString()}"
        }
        if(selectedDay.length == 1){
            selectedDay = "0$selectedDay"
        }

        TimePickerDialog(this,this,hour, minute,true).show()

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        selectedHour = hourOfDay.toString()
        selectedMinute = minute.toString()

        if(selectedHour.length == 1){
            selectedHour = "0${selectedHour.toString()}"
        }
        if(selectedMinute.length == 1){
            selectedMinute = "0$selectedMinute"
        }

        datetime = "$selectedDay-$selectedMonth-$selectedYear  $selectedHour:$selectedMinute:00"
        setDateText(num)

    }


    /*@RequiresApi(Build.VERSION_CODES.N)
    fun selectDate (): String{
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)
        var date:String = ""

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            date = "$dayOfMonth-$monthOfYear-$year"

        }, year, month, day)

        dpd.show()

        val tpd = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->

            date += " $hour:$minute"
        }

        return date

    }*/



}