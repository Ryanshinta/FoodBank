package com.taruc.foodbank

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthException
import com.taruc.foodbank.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        auth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener() {
            registerNewUser()
        }

    }

    private fun registerNewUser() {
        var email: String
        var password: String
        var RepeatPassword: String

        email = binding.etEmail.toString()
        password = binding.EtPassword.toString()
        RepeatPassword = binding.password2.toString()
//
//        if(password != RepeatPassword){
//            Toast.makeText(applicationContext,"Password and Confirm Password do not match",Toast.LENGTH_LONG).show()
//            return;
//        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter email!", Toast.LENGTH_LONG).show()
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
            return;
        }
//        if (TextUtils.isEmpty(RepeatPassword)) {
//            Toast.makeText(applicationContext, "Please enter Repeat Password!", Toast.LENGTH_LONG)
//                .show()
//            return;
//        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Registration successful!",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    try {
                        throw task.exception!!
                    }catch (e:FirebaseAuthActionCodeException){

                    }catch (e:FirebaseAuthException){

                    }catch (e:FileAlreadyExistsException){

                    }catch (e:FirebaseException){

                    }
                    Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
                }
            }

    }

}