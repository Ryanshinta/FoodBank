package com.taruc.foodbank

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

class SendEmailDonationActivity : AppCompatActivity() {
//    lateinit var receiverEmail : EditText
    lateinit var emailSubject : EditText
    lateinit var emailContent : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_email_donation)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.admin)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3640FF")))

        val sendButton = findViewById<Button>(R.id.btn_send)
//        receiverEmail = findViewById(R.id.emailAddress)
        emailSubject = findViewById(R.id.subject)
        emailContent = findViewById(R.id.message)

        val email = intent.getStringExtra("email")

        sendButton.setOnClickListener{

            var emailAddress = email
            var subject = emailSubject.text.toString().trim()
            var message = emailContent.text.toString().trim()

            var i = Intent(Intent.ACTION_SEND)

            i.data = Uri.parse("Mail to: ")
            i.type = "text/plain"

            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
            i.putExtra(Intent.EXTRA_SUBJECT,subject)
            i.putExtra(Intent.EXTRA_TEXT,message)

            startActivity(Intent.createChooser(i,"Choose Email Cliet"))
        }
    }
}