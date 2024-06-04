package com.example.shopease

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Floating_Button : AppCompatActivity() {

    lateinit var txtHead:TextView
    lateinit var edtEmail:EditText
    lateinit var edtPassword:EditText
    lateinit var btnSignIn:Button
    lateinit var btnSignUp:Button
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_button)

        txtHead=findViewById(R.id.txt_head)
        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        btnSignIn=findViewById(R.id.btn_signIn)
        btnSignUp=findViewById(R.id.btn_signUp)
        mAuth=Firebase.auth

        val mSpannableString = SpannableString(txtHead.text)
        mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
        txtHead.text=mSpannableString

        btnSignIn.setOnClickListener {
            val email:String=edtEmail.text.toString()
            val password:String=edtPassword.text.toString()
            login(email,password)
        }
        btnSignUp.setOnClickListener {
            val email:String=edtEmail.text.toString()
            val password:String=edtPassword.text.toString()
            signUp(email,password)
        }
    }

    private fun login(Email:String,Password:String)
    {
            mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Open Upload Product Detail Activity
                        startActivity(
                            Intent(this,ListProduct::class.java)
                        )
                    } else {
                        Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show()
                    }

        }
    }

    private fun signUp(Email: String,Password: String)
    {
        mAuth.createUserWithEmailAndPassword(Email,Password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Open Upload Product Detail Activity
                    startActivity(
                        Intent(this,ListProduct::class.java)
                    )
                } else {
                    Toast.makeText(this, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                }

            }
    }
}