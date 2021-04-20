package com.example.firebasefirst


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast

import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_verification.*
import java.lang.IndexOutOfBoundsException
import java.util.concurrent.TimeUnit


class Verification : AppCompatActivity() {

    lateinit var originalPhone:String
    var verificationId = ""
    private lateinit var mAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        mAuth = FirebaseAuth.getInstance()


        var formatterPhone = intent.extras?.get("KEY").toString()

        originalPhone = formatterPhone

        formatterPhone = formatPhone(formatterPhone)

        tvNumber.text = "Bir martalik kod $formatterPhone\n raqamiga jo'natildi."


            startTime()

        sendVerifation(originalPhone)




        editTextSMS.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 6){

                    verifyCode(s?.toString())
                }else{
                    Toast.makeText(applicationContext, "Parollarda moslik yoq", Toast.LENGTH_SHORT).show()
                }

            }
        })



   //qayta sms jonatish
        btnRetry.setOnClickListener {
            editTextSMS.text?.clear()
            startTime()
            sendVerifation(originalPhone)
        }

    }

    private fun startTime(){
        object : CountDownTimer(60000, 1000 + 100){
            override fun onFinish() {
           btnRetry.visibility = View.VISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {
             val mill = (millisUntilFinished /1000).toInt()
                updateTimer(mill)
                btnRetry.visibility = View.INVISIBLE
            }

        }.start()
    }

    private fun updateTimer(secondLeft: Int) {
       val minutes = secondLeft / 60
        val seconds = secondLeft - minutes*60

        var seconndStr = seconds.toString()
        var minutesStr = minutes.toString()

        if (seconndStr == "0"){
            seconndStr = "00"
        }
        if (seconndStr.length == 1){
            seconndStr = "0$seconndStr"
        }

        if (minutesStr.length == 1){
            minutesStr = "0$minutesStr"
        }

        tvTimer.text = "$minutesStr:$seconndStr"
    }



     private fun sendVerifation(phone: String){

         PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 phone,120, TimeUnit.SECONDS, this,
                 object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                     override fun onVerificationCompleted(authCredential: PhoneAuthCredential) {
                       val code = authCredential.smsCode
                    //   verifyCode(e)

                     }

                     override fun onVerificationFailed(p0: FirebaseException) {
                         //Log.d("ERROR", p0.toString())
                     }

                     override fun onCodeSent(verificationCode: String, p1: PhoneAuthProvider.ForceResendingToken) {
                         verificationId = verificationCode
                         Log.d("ERROR", "On code sent--> $verificationId ")

                     }
                 }
         )
     }

    fun verifyCode(code:String){

        val credential = PhoneAuthProvider.getCredential(verificationId,code)
        signInWithCredential(credential)
    }

    fun signInWithCredential(credential: PhoneAuthCredential){
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                val intent = Intent(applicationContext,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }else{
                Log.d("ERROR", task.exception?.message.toString())
                Toast.makeText(applicationContext, "Karocchi xato", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun formatPhone(formatterPhone: String):String {

        try {
            var str = ""
            if (formatterPhone.startsWith("+")){
                 str =  String.format(
                    "%s%s%s%s (%s%s) %s%s%s-%s%s-%s%s",
                    formatterPhone[0],
                    formatterPhone[1],
                    formatterPhone[2],
                    formatterPhone[3],
                    formatterPhone[4],
                    formatterPhone[5],
                    formatterPhone[6],
                    formatterPhone[7],
                    formatterPhone[8],
                    formatterPhone[9],
                    formatterPhone[10],
                    formatterPhone[11],
                    formatterPhone[12]
                )
            }else{
               str = String.format(
                    "+%s%s%s (%s%s) %s%s%s - %s%s - %s%s",
                    formatterPhone[0],
                    formatterPhone[1],
                    formatterPhone[2],
                    formatterPhone[3],
                    formatterPhone[4],
                    formatterPhone[5],
                    formatterPhone[6],
                    formatterPhone[7],
                    formatterPhone[8],
                    formatterPhone[9],
                    formatterPhone[10],
                    formatterPhone[11]
                )
            }

            return str
        }catch (ex:IndexOutOfBoundsException){
            ex.printStackTrace()
        }
     return ""

    }
}
