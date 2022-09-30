package com.taruc.foodbank

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.taruc.foodbank.entity.event
import java.text.SimpleDateFormat
import java.util.*

class admin_Activity_Event_Detail : AppCompatActivity()  , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0
    var datetime: String = "DATE"
    var startNum:String = ""
    var endNum:String = ""
    var num = 0
    var arrayLocation = DoubleArray(2) {0.0}

    var selectedDay = ""
    var selectedMonth = ""
    var selectedYear = 0
    var selectedHour = ""
    var selectedMinute = ""
    private lateinit var imgUri: Uri
    private lateinit var ref: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_event_detail)
        ref = FirebaseStorage.getInstance().reference
        val event  = intent.getParcelableExtra<event>("event")

        if(event!= null){

            val btnStartUpdateDate = findViewById<Button>(R.id.btnStartUpdateDate)
            val btnEndUpdateDate = findViewById<Button>(R.id.btnEndUpdateDate)
            val btnEventUpdateImage = findViewById<Button>(R.id.btnEventUpdateImage)
            val btnUpdateEvent = findViewById<Button>(R.id.btnUpdateEvent)
            val tfEventUpdateAddress = findViewById<TextView>(R.id.tfEventUpdateAddress)
            val tfEventUpdateName = findViewById<TextView>(R.id.tfEventUpdateName)
            val tfEventUpdateDescription = findViewById<TextView>(R.id.tfEventUpdateDescription)
            val imgUpdateEvent = findViewById<ImageView>(R.id.imgUpdateEvent)
            val btnDeleteEventAdmin = findViewById<Button>(R.id.btnDeleteEventAdmin)

            btnStartUpdateDate.text = event.dateStart
            btnEndUpdateDate.text = event.dateEnd
            tfEventUpdateAddress.text = event.address
            tfEventUpdateName.text = event.name
            tfEventUpdateDescription.text = event.description
            arrayLocation[0] = event.latitude!!
            arrayLocation[1] = event.longtitude!!
/*
            imgUpdateEvent.
*/
            val createDate = event.createdDate.toString()



            btnStartUpdateDate.setOnClickListener(){

                getDateTimeCalender()

                num = 1

                DatePickerDialog(this, this, year, month, day).show()

            }

            btnEndUpdateDate.setOnClickListener(){
                getDateTimeCalender()

                num = 2
                DatePickerDialog(this, this, year, month, day).show()

            }

            btnEventUpdateImage.setOnClickListener(){
                getImage.launch("image/*")
            }

            btnDeleteEventAdmin.setOnClickListener(){
                val db = FirebaseFirestore.getInstance()
                db.collection("events").document("$createDate")
                    .update("status", "Inactive")
                    .addOnSuccessListener { Log.d(TAG, " successfully deleted!")
                        val intent6 = Intent(this, admin_Activity_Event::class.java)
                        startActivity(intent6)

                        finish()
                    }
                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

            }

            btnUpdateEvent.setOnClickListener(){

                //validate Empty
                if(tfEventUpdateName.text.trim().isEmpty()) {
                    Toast.makeText(applicationContext, "Please Enter Event Name", Toast.LENGTH_SHORT).show()
                }else if(tfEventUpdateDescription.text.trim().isEmpty()){
                    Toast.makeText(applicationContext, "Please Enter Event Description", Toast.LENGTH_SHORT).show()
                }else if(btnStartUpdateDate.text == "DATE"){
                    Toast.makeText(applicationContext, "Please select Start Date", Toast.LENGTH_SHORT).show()
                }
                else if(btnEndUpdateDate.text == "DATE"){
                    Toast.makeText(applicationContext, "Please select End Date", Toast.LENGTH_SHORT).show()
                }
                else if(tfEventUpdateAddress.text.trim().isEmpty()){

                    Toast.makeText(applicationContext, "Please Enter Event Address", Toast.LENGTH_SHORT).show()


                }
                else if(imgUpdateEvent.drawable == null){

                    Toast.makeText(applicationContext, "Please Enter Event Image", Toast.LENGTH_SHORT).show()

                }else {
                    getLocationFromAddress(tfEventUpdateAddress.text.toString())

                    //validate data validity
                    if(btnStartUpdateDate.text.toString() > btnEndUpdateDate.text.toString()){
                        Toast.makeText(applicationContext, "Start Date must be earlier than End Date", Toast.LENGTH_SHORT).show()
                    }else if(arrayLocation[0] == 0.0){
                        Toast.makeText(applicationContext, "Address Cannot Recognized", Toast.LENGTH_SHORT).show()
                    }else{
                        //add to firestore
                        val imgName = ref.child("eventImg/$createDate.png")
                        imgName.putFile(imgUri)
                        val db = FirebaseFirestore.getInstance()
                        val event = hashMapOf(
                            "address" to tfEventUpdateAddress.text.trim().toString(),
                            "dateEnd" to btnEndUpdateDate.text.toString(),
                            "dateStart" to btnStartUpdateDate.text.toString(),
                            "description" to tfEventUpdateDescription.text.trim().toString(),
                            "image" to "$createDate.png",
                            "createdDate" to createDate,
                            "latitude" to arrayLocation[0].toDouble(),
                            "longtitude" to arrayLocation[1].toDouble(),
                            "name" to tfEventUpdateName.text.trim().toString(),
                            "status" to "Active"
                        )

                        db.collection("events")
                            .document(createDate).set(event)
                            .addOnSuccessListener { Toast.makeText(this,"Update Successfully", Toast.LENGTH_SHORT).show()
                                val intent5 = Intent(this, admin_Activity_Event::class.java)
                                startActivity(intent5)

                                finish()

                            }
                            .addOnFailureListener { Toast.makeText(this,"Update Failed", Toast.LENGTH_SHORT).show()}
                    }
                }

            }
        }

    }

    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri ->

        val btnUploadImage = findViewById<Button>(R.id.btnEventUpdateImage)
        imgUri = uri!!

        val imgUpdateEvent = findViewById<ImageView>(R.id.imgUpdateEvent)
        imgUpdateEvent.setImageURI(uri)
    }

    private fun getLocationFromAddress(strAddress: String?){
        val coder = Geocoder(this)
        var address: List<Address>?
        val btnUpdateEvent = findViewById<Button>(R.id.btnUpdateEvent)
        arrayLocation[0] = 0.0
        arrayLocation[1] = 0.0


        address = coder.getFromLocationName(strAddress, 5)
        if (address.isNotEmpty()) {


            val location = address[0]
            arrayLocation[0] = location.latitude.toDouble()
            arrayLocation[1] = location.longitude.toDouble()
        }

    }

    private fun setDateText (num:Int){
        val btnStartUpdateDate = findViewById<Button>(R.id.btnStartUpdateDate)
        val btnEndUpdateDate = findViewById<Button>(R.id.btnEndUpdateDate)

        if(num == 1){
            startNum = datetime
            btnStartUpdateDate.text = datetime

        }else{
            endNum = datetime
            btnEndUpdateDate.text = datetime
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

        datetime = "$selectedDay-$selectedMonth-$selectedYear $selectedHour:$selectedMinute:00"
        setDateText(num)

    }
}