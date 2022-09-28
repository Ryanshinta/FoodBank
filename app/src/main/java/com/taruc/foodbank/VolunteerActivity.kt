package com.taruc.foodbank

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.taruc.foodbank.databinding.ActivityLoginBinding
import com.taruc.foodbank.databinding.ActivityVolunteerBinding
import com.taruc.foodbank.entity.role

class VolunteerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVolunteerBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_volunteer)

        auth = FirebaseAuth.getInstance();
        db = Firebase.firestore

        binding.btnVolRegister.setOnClickListener() {
            registerVolunteer()
        }
    }

    private fun registerVolunteer() {
        val name: String = binding.tvVolName.text.toString()
        val age: String = binding.tvVolAge.text.toString()
        val email: String = binding.tvVolEmail.text.toString()
        //val cbFoodBankVolunteer = binding.cbFoodBankHelper
        //val cbEventVolunteer = binding.cbEventHelper
        val button = binding.btnVolRegister

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(applicationContext, "Please enter your name!", Toast.LENGTH_LONG).show()
            return;
        }
        if (TextUtils.isEmpty(age)) {
            Toast.makeText(applicationContext, "Please enter your age!", Toast.LENGTH_LONG).show()
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter your email!", Toast.LENGTH_LONG).show()
            return;
        }

    }
}
