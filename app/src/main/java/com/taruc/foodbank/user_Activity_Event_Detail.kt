package com.taruc.foodbank

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.taruc.foodbank.entity.event
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

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
            val btnCalendar = findViewById<Button>(R.id.btnCalendar)
/*
            val imgEvent = findViewById<ImageView>()
*/

            tvEventName.text = event.name
            tfDescription.text = event.description
            tfDateStart.text = event.dateStart.toString()
            tfDateEnd.text = event.dateEnd.toString()
            tfAddress.text = event.address


            btnCalendar.setOnClickListener(){
                addCalendar(event)


            }



/*
            imgEvent.setImageResource
*/
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun addCalendar(event: event) {



        val date2 = event.dateStart
        val formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss", Locale.KOREA)
        val localDate2: LocalDateTime = LocalDateTime.parse(date2, formatter2)
/*
        localDate2.minus(Duration.ofHours(8))
*/
        val timeInMilliseconds2: Long = localDate2.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()

        val date = event.dateEnd
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss", Locale.ENGLISH)
        val localDate: LocalDateTime = LocalDateTime.parse(date, formatter)
/*
        localDate.minus(Duration.ofHours(8))
*/
        val timeInMilliseconds: Long = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()


        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, event.name)
            putExtra(CalendarContract.Events.EVENT_LOCATION, event?.address.toString())
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timeInMilliseconds2)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, timeInMilliseconds)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}