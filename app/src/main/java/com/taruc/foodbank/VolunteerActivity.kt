package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.taruc.foodbank.entity.event
import java.util.*


class VolunteerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)

        var i = getIntent()
        var Eventname = i.getStringExtra("name")

        val btnVolRegister = findViewById<Button>(R.id.btnVolRegister)

        btnVolRegister.setOnClickListener(){
            val auth = FirebaseAuth.getInstance()

            var name = findViewById<TextView>(R.id.tvVolName)
            var age = findViewById<TextView>(R.id.tvVolAge)
            var contact = findViewById<TextView>(R.id.tvVolContact)
            var Useremail = auth.currentUser?.email.toString()

            val Volname = name.text.toString()
            val Volage = age.text.toString()
            val Volcontact = contact.text.toString()
            val VolEmail = Useremail
            val db = FirebaseFirestore.getInstance()


            saveVolunteer(Volname, Volage, Volcontact, VolEmail, Eventname )

            if (name == null) {
                Toast.makeText(applicationContext, "Please enter your name!", Toast.LENGTH_LONG).show()
            }
            if (age == null) {
                Toast.makeText(applicationContext, "Please enter your age!", Toast.LENGTH_LONG).show()
            }
            if (contact == null) {
                Toast.makeText(applicationContext, "Please enter your Phone number!", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun saveVolunteer(
        Volname: String,
        Volage: String,
        Volcontact: String,
        Volemail: String,
        Eventname: String?
    ){
        val db = FirebaseFirestore.getInstance()
        val volunteer = hashMapOf(
            "Name" to Volname,
            "Age" to Volage,
            "Contact" to Volcontact,
            "Email" to Volemail,
            "Status" to "Registered",
            "Event" to Eventname
        )
        var volunteerEvent = 1;
        db.collection("events").document(Eventname!!).get()
            .addOnSuccessListener {
                val event = it.toObject<event>()
                if (Objects.nonNull(event)){
                    volunteerEvent = event!!.ttlVolunteer
                    if (volunteerEvent == 0){
                        volunteerEvent++
                    }
                }
        }
        db.collection("events").document(Eventname!!).update("ttlVolunteer",volunteerEvent)

        db.collection("volunteer")
            .document().set(volunteer)
            .addOnSuccessListener { Toast.makeText(this,"You have became a volunteer", Toast.LENGTH_SHORT).show()}
            .addOnFailureListener {Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()}
    }


}
