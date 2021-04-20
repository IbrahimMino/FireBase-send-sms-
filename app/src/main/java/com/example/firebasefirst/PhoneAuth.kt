package com.example.firebasefirst

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PhoneAuth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)

        val btn = findViewById<Button>(R.id.btnKirish)
        val edt = findViewById<EditText>(R.id.editText)

        btn.setOnClickListener {
            if (edt.text.isNotEmpty()){
                val intent = Intent(applicationContext,Verification::class.java)
                intent.putExtra("KEY",edt.text.toString())
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(applicationContext, "Raqam kiriting!", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onStart() {
        super.onStart()
        //sharedPreferencega true false qilib saqlaymiz.
    }
}