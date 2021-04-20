package com.example.firebasefirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign.*
import java.net.Authenticator

class SignActivity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        auth = Firebase.auth



        btnSign.setOnClickListener {
            if (edtSignLogin.text.isNotEmpty() && edtSignPass.text.isNotEmpty() && edtSignPass.text.length>6){
                      val email = edtSignLogin.text.toString()
                val pass = edtSignPass.text.toString()

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this) {
                    task ->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext, "Sign Up Succesfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext,LoginActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(applicationContext, "Response", Toast.LENGTH_SHORT).show()
                    }
                }

            }else{
                Toast.makeText(applicationContext, "Ma'lumotlar to'liq kiritilmadi!!!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}