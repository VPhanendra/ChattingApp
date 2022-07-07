package com.example.chattingApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chatting_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var name : EditText
    private lateinit var signup : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDataRef :DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        email =findViewById(R.id.edi_email)
        password =findViewById(R.id.edi_password)
        name =findViewById(R.id.edi_name)
        signup =findViewById(R.id.edi_signUp)
        signup.setOnClickListener {
            val name = name.text.toString()
            val email = email.text.toString()
            val passowrd =password.text.toString()
            signup(name,email,passowrd)
        }
    }
    private fun signup( name:String,email:String, passowrd :String ){
        mAuth.createUserWithEmailAndPassword(email, passowrd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDataBase(name,email,mAuth.currentUser?.uid!!   )
                    //code for move to main activity
                    val intent =Intent(this@SignUp,MainActivity::class.java)
                       finish()
                        startActivity(intent)
                } else {

                    Toast.makeText(this@SignUp,"some error occures",Toast.LENGTH_SHORT).show()

                }
            }

    }
    private fun addUserToDataBase(name: String,email: String,uid:String){
    mDataRef = FirebaseDatabase.getInstance().getReference()
        mDataRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}