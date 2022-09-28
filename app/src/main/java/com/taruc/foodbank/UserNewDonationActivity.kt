package com.taruc.foodbank

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taruc.foodbank.entity.Donation
import com.taruc.foodbank.entity.foodBank
import java.util.*
import kotlin.collections.ArrayList

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
    lateinit var foodbankNames:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_new_donation)

        val buttonChoose = findViewById<Button>(R.id.btn_choose)
        foodbankNames = arrayListOf()

        buttonChoose.setOnClickListener {
            getDateTimeCalender()

            DatePickerDialog(this, this, year, month, day).show()
        }

        val buttonDonate = findViewById<Button>(R.id.btn_confirmDonation)
        buttonDonate.setOnClickListener{
            val auth = FirebaseAuth.getInstance()
            val type = findViewById<RadioGroup>(R.id.radio_group)

            var donateTo = findViewById<Spinner>(R.id.sp_foodBank)
            val donorEmail = auth.currentUser?.email.toString()
            var food = findViewById<TextView>(R.id.tf_foodItem)
            var foodQty = findViewById<TextView>(R.id.tf_foodQty)
            var foodType = type.checkedRadioButtonId
            var pickupAddress = findViewById<TextView>(R.id.tf_pickupLocation)
            var pickupDateTime = findViewById<TextView>(R.id.tf_pickedDateTime)
            var status = "Pending"

            val foodBank = donateTo.selectedItem.toString()
            val email = donorEmail
            val foodItem = food.text.toString()

            saveDonation(foodBank, email, foodItem)
        }

        //get firestore data to spinner
        val db = FirebaseFirestore.getInstance()

        val spinner: Spinner = findViewById<Spinner>(R.id.sp_foodBank)
        db.collection("foodbanks")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var foodBankName = document.toObject(foodBank::class.java).name
                    foodbankNames.add(foodBankName)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        foodbankNames = arrayListOf("--Choose a food bank--")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, foodbankNames)
        spinner.adapter = adapter
        spinner.setSelection(0)
    }

    fun saveDonation(foodBank: String, email : String, foodItem : String){
        val db = FirebaseFirestore.getInstance()
        val donation = hashMapOf(
            "donateTo" to foodBank,
            "donorEmail" to email,
            "food" to foodItem
        )

        db.collection("donations")
            .document().set(donation)
            .addOnSuccessListener { Toast.makeText(this,"Added Successfully", Toast.LENGTH_SHORT).show()}
            .addOnFailureListener {Toast.makeText(this,"Added Failed", Toast.LENGTH_SHORT).show()}
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

        val choosedDateTime = findViewById<TextView>(R.id.tf_pickedDateTime)
        choosedDateTime.text = "$selectedDay/$selectedMonth/$selectedYear  $selectedHour:$selectedMinute"
    }
}