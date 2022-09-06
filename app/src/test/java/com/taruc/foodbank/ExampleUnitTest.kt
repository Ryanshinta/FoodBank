package com.taruc.foodbank

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var auth: FirebaseAuth
    @Test
    fun addition_isCorrect() {
        auth = FirebaseAuth.getInstance()
        var email:String = "123@gmail.com"
        var password:String = "987123"
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(){
                task -> if(task.isSuccessful){
            println("1")
        }else{
            println("0")
        }
        }
    }
}