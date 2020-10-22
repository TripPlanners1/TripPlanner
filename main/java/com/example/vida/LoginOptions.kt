package com.example.vida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginOptions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_options)
    }
    fun signup(view: View){
        val intent = Intent(this, SignupScreen::class.java)
        startActivity(intent)
    }
    fun home(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}