package com.taruc.foodbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        android.os.Handler().postDelayed({val intent = Intent(this@MainActivity, activityLogin::class.java)
            startActivity(intent)
            finish()} , 3000)
    }
}