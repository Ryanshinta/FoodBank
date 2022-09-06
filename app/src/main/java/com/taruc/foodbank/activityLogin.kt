package com.taruc.foodbank


import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth

import com.taruc.foodbank.databinding.ActivityLoginBinding


class activityLogin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener(){
            login()
        }

        binding.btnRegister.setOnClickListener(){
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        val email = binding.etEmail.toString()
        val password = binding.EtPassword.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                task -> if (task.isSuccessful){
                    Toast.makeText(this, "Successfully LoggedIn",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Log In failed",Toast.LENGTH_LONG).show()

            }
            }
    }


}