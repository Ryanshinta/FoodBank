package com.taruc.foodbank

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.taruc.foodbank.databinding.ActivityRegisterBinding
import com.taruc.foodbank.entity.role
import com.taruc.foodbank.entity.user


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        auth = FirebaseAuth.getInstance();
        db = Firebase.firestore

        binding.btnRegister.setOnClickListener() {
            registerNewUser()
        }

    }

    private fun registerNewUser() {

        var email: String = binding.etEmail.text.toString()
        var name:String = binding.etName.text.toString()
        var password: String = binding.EtPassword.text.toString()
        var RepeatPassword: String = binding.password2.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter email!", Toast.LENGTH_LONG).show()
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
            return;
        }
        if (TextUtils.isEmpty(RepeatPassword)) {
            Toast.makeText(applicationContext, "Please enter Repeat Password!", Toast.LENGTH_LONG)
                .show()
            return;
        }
        if(password != RepeatPassword){
            Toast.makeText(applicationContext,"Password and Confirm Password do not match",Toast.LENGTH_LONG).show()
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Registration successful!",
                        Toast.LENGTH_LONG
                    ).show()
                    val user = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "role" to role.USER
                    )
                    db.collection("users")
                        .document(email)
                        .set(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference.toString()}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error adding document", e)
                        }
                    val intent = Intent(this, activityLogin::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    try {
                        Log.e("Register error",task.exception.toString())
                        throw task.exception!!
                    }catch (e:FirebaseAuthWeakPasswordException){
                        throw e
                    }catch (e:FirebaseAuthInvalidCredentialsException){
                        throw e
                    }catch (e:FirebaseAuthUserCollisionException){
                        throw e
                    }catch (e:FirebaseException){

                    }
                    Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
                }
            }

    }


}