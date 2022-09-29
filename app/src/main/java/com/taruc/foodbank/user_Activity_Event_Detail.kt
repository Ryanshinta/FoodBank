package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.taruc.foodbank.entity.event

class user_Activity_Event_Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_event_detail)

        val event  = intent.getParcelableExtra<event>("event")
        if(event!= null){
            val tvEventName = findViewById<TextView>(R.id.tvEventName)
/*
            val imgEvent = findViewById<ImageView>()
*/
            tvEventName.text = event.name


/*
            imgEvent.setImageResource
*/
        }
    }
}