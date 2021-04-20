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

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        tvAccaunt.setOnClickListener {
            startActivity(Intent(applicationContext,SignActivity::class.java))
            finish()
        }


        btnLogin.setOnClickListener {
            if (edtLogin.text.isNotEmpty() && edtPass.text.isNotEmpty() && edtPass.text.length>6){
                val email = edtLogin.text.toString()
                val pass = edtPass.text.toString()

               auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this){
                   task ->
                   if (task.isSuccessful){
                       startActivity(Intent(applicationContext,MainActivity::class.java))
                       finish()
                   }else{
                       Toast.makeText(applicationContext, "Bunday foydalanuvchi mavjud emas!!!", Toast.LENGTH_SHORT).show()
                   }
               }



            }else{
                Toast.makeText(applicationContext, "Ma'lumotlar to'liq kiritilmadi!!!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}