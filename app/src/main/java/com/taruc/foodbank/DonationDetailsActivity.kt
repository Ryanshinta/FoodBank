package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class DonationDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_details)

        val spinner = findViewById<Spinner>(R.id.spinner_text)

        val text = arrayListOf<String>("1", "2", "3", "4","5")
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,text)

        spinner.adapter = adapter
        spinner.setSelection(0)


    }
}