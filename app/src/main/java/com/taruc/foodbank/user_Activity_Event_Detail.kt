package com.taruc.foodbank

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.taruc.foodbank.entity.event
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class user_Activity_Event_Detail : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_event_detail)

        val event  = intent.getParcelableExtra<event>("event")
        if(event!= null){
            val tvEventName = findViewById<TextView>(R.id.tvEventName)
            val tfDescription = findViewById<TextView>(R.id.tfDescription)
            val tfDateStart = findViewById<TextView>(R.id.tfDateStart)
            val tfDateEnd = findViewById<TextView>(R.id.tfDateEnd)
            val tfAddress = findViewById<TextView>(R.id.tfAddress)
/*
            val imgEvent = findViewById<ImageView>()
*/

            tvEventName.text = event.name
            tfDescription.text = event.description
            tfDateStart.text = event.dateStart.toString()
            tfDateEnd.text = event.dateEnd.toString()
            tfAddress.text = event.address


/*
            imgEvent.setImageResource
*/
        }
    }
}