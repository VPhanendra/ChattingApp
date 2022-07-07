package com.example.chattingApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chatting_app.R
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var email :EditText
    private lateinit var password :EditText
    private lateinit var login :Button
    private lateinit var signup :Button
    private lateinit var mAuth :FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        email =findViewById(R.id.email)
        password =findViewById(R.id.password)
        login =findViewById(R.id.login)
        signup =findViewById(R.id.signUp)
        signup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {
            val email = email.text.toString()
            val passowrd =password.text.toString()
            if (validateInput()) {
                loging(email, passowrd)
            }
        }
    }
    private fun loging(email:String,passowrd:String){
        mAuth.signInWithEmailAndPassword(email, passowrd)
            .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //code for logining  for user
                        val intent = Intent(this@Login, MainActivity::class.java)
                       finish()
                        startActivity(intent)
                    } else {

                        Toast.makeText(this@Login, "user dose not exist ", Toast.LENGTH_SHORT)
                            .show()

                    }

            }
    }
    fun validateInput() :Boolean{
        if (email.text.toString() == "") {
            email.error = "Please Enter Email"
            return false
        }
        if (password.text.toString() == "") {
            password.error = "Please Enter Password"
            return false
        }

        // checking the proper email format
        if (!isEmailValid(email.text.toString())) {
            email.error = "Please Enter Valid Email"
            return false
        }
        return true
    }
    fun isEmailValid(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}