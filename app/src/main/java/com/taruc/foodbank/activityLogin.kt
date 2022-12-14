package com.taruc.foodbank


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthException

import com.taruc.foodbank.databinding.ActivityLoginBinding
import kotlin.Exception


class activityLogin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        auth.currentUser
        binding.btnLogin.setOnClickListener() {
            login()
        }

        binding.btnRegister.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.EtPassword.text.toString()


        var auth: FirebaseAuth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.i("Login Success",task.result.toString())
                    Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_LONG).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("Login error",task.exception.toString())
                    Toast.makeText(this, "Log In failed", Toast.LENGTH_LONG).show()
                }
            }
//            .addOnFailureListener(){
//                val errorCode = (task.exception as FirebaseAuthException).errorCode
//                val errorMessage = authErrors[errorCode] ?: R.string.error_login_default_error
//                Toast.makeText(this, this.getString(errorMessage),Toast.LENGTH_LONG).show()
//
//            }


    }


}