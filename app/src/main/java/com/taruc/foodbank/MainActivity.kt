package com.taruc.foodbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.taruc.foodbank.entity.role
import com.taruc.foodbank.entity.user

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private lateinit var userObj:user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        val db = Firebase.firestore



        if (user == null){
            Toast.makeText(
                applicationContext,
                "login first",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(
                this, activityLogin::class.java
            )
            startActivity(intent)
        }else{
            Log.i("Login Success", user.email.toString())

            val userRef = db.collection("users").document(user?.email.toString())
            userRef.get().addOnSuccessListener {
                if (it != null){
                    userObj = it.toObject(com.taruc.foodbank.entity.user::class.java)!!

                    if (userObj.role == role.ADMIN){
                        val intent = Intent(
                            this, admin_activity::class.java
                        )
                        startActivity(intent)
                    }
                }
            }

        }






        navView.setNavigationItemSelectedListener {
            val id = it.itemId

            when (id) {
                R.id.nav_food_bank -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Food Bank",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        this, userFoodBankActivity::class.java
                    )
                    startActivity(intent)
                }
                R.id.nav_donation -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Donation",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        this, UserDonationActivity::class.java
                    )
                    startActivity(intent)
                }
                R.id.nav_application -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Application",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        this, UserDonationActivity::class.java
                    )
                    startActivity(intent)
                }
                R.id.nav_event -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Event",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        this, UserDonationActivity::class.java
                    )
                    startActivity(intent)
                }
                R.id.nav_volunteer -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Volunteer",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        this, UserDonationActivity::class.java
                    )
                    startActivity(intent)
                }
                R.id.nav_user_profile -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked My Profile",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        this, UserProfileActivity::class.java
                    )
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Logout",
                        Toast.LENGTH_SHORT
                    ).show()
                    auth.signOut()
                    finish()
                    startActivity(intent)

                }
                R.id.nav_share -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Share",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        this, UserDonationActivity::class.java
                    )
                    startActivity(intent)
                }
                R.id.nav_setting -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Setting",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        this, UserDonationActivity::class.java
                    )
                    startActivity(intent)
                }
                R.id.nav_about_us -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked About Us",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(
                        this, UserDonationActivity::class.java
                    )
                    startActivity(intent)
                }
                else -> {
                    Toast.makeText(
                        applicationContext,
                        "No available",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
//}
//        supportActionBar?.hide()
//
//        android.os.Handler().postDelayed({val intent = Intent(this@MainActivity, activityLogin::class.java)
//            startActivity(intent)
//            finish()} , 3000)