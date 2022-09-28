package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class VolunteerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)

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


            saveVolunteer(Volname, Volage, Volcontact, VolEmail )

            if (name != name) {
                Toast.makeText(applicationContext, "Please enter your name!", Toast.LENGTH_LONG).show()
            }
            if (age != age) {
                Toast.makeText(applicationContext, "Please enter your age!", Toast.LENGTH_LONG).show()
            }
            if (contact != contact) {
                Toast.makeText(applicationContext, "Please enter your Phone number!", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun saveVolunteer(Volname: String, Volage : String, Volcontact : String, Volemail : String){
        val db = FirebaseFirestore.getInstance()
        val volunteer = hashMapOf(
            "Name" to Volname,
            "Age" to Volage,
            "Contact" to Volcontact,
            "Email" to Volemail
        )

        db.collection("volunteer")
            .document().set(volunteer)
            .addOnSuccessListener { Toast.makeText(this,"You have became a volunteer", Toast.LENGTH_SHORT).show()}
            .addOnFailureListener {Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()}
    }


}
