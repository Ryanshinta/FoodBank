package com.taruc.foodbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.taruc.foodbank.databinding.ActivityForgotPasswordBinding
import com.taruc.foodbank.databinding.ActivityLoginBinding
// todo forgotPassword
class forgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        binding.btnReset.setOnClickListener() {
            var email = binding.etEmail.text.toString().trim();

            if (email.isBlank() || email.isEmpty()) {
                Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show()
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email is invalid", Toast.LENGTH_LONG).show()
            }

            auth.sendPasswordResetEmail(email).addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Already reset you password, Pls check your email",
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                    Toast.makeText(
                        this,
                        task.exception.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}