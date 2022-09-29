package com.taruc.foodbank

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.taruc.foodbank.entity.foodBank
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class UserNewApplicationActivity : AppCompatActivity(){
    lateinit var emergencyLevel:ArrayList<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_new_application)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        emergencyLevel = arrayListOf()

        var applyBy = findViewById<TextView>(R.id.tf_applyBy)
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.email.toString()

        val db = FirebaseFirestore.getInstance()
        val usersRef = db.collection("users")
        usersRef.document(uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    val name = document.getString("name")
                    applyBy.text = name.toString()
                    Log.d(TAG,"$name/")
                } else {
                    Log.d(TAG, "The document doesn't exist.")
                }
            } else {
                task.exception?.message?.let {
                    Log.d(TAG, it)
                }
            }
        }
        val buttonApply = findViewById<Button>(R.id.btn_confirmApplication)
        buttonApply.setOnClickListener{
            var resources = findViewById<TextView>(R.id.tf_resources)
            var amount = findViewById<TextView>(R.id.tf_amount)
            var description = findViewById<TextView>(R.id.tf_description)
            var emergencyLevel = findViewById<Spinner>(R.id.sp_emergencyLevel)
            var status = "Pending"
            val auth = FirebaseAuth.getInstance()
            val applyEmail = auth.currentUser?.email.toString()

            val name = applyBy.text.toString()
            val resourcesName = resources.text.toString()
            val resourceAmount = amount.text.toString().toInt()
            val desc = description.text.toString()
            val emergencyLvl = emergencyLevel.selectedItem.toString()
            val dtCreated = LocalDateTime.now().toString()

            saveApplication(name, resourcesName, resourceAmount, desc, emergencyLvl, status, dtCreated, applyEmail)
        }

        //get firestore data to spinner
        val spinner: Spinner = findViewById<Spinner>(R.id.sp_emergencyLevel)
        emergencyLevel = arrayListOf("--Select a Level--", "Urgent", "Emergency", "Critical")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emergencyLevel)
        spinner.adapter = adapter
        spinner.setSelection(0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveApplication(name : String, resourcesName : String, resourceAmount : Int, desc : String, emergencyLvl : String, status : String, dtCreated : String, applyEmail : String){
        val db = FirebaseFirestore.getInstance()
        val application = hashMapOf(
            "applicationStatus" to status,
            "applyBy" to name,
            "dateTimeApplied" to dtCreated,
            "description" to desc,
            "emergencyLevel" to emergencyLvl,
            "resourcesAmount" to resourceAmount,
            "resourcesName" to resourcesName,
            "applyEmail" to applyEmail
        )

        db.collection("applications")
            .document(dtCreated).set(application)
            .addOnSuccessListener { Toast.makeText(this,"Added Successfully", Toast.LENGTH_SHORT).show()}
            .addOnFailureListener {Toast.makeText(this,"Added Failed", Toast.LENGTH_SHORT).show()}
    }
}