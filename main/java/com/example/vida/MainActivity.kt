package com.example.vida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun login(view: View){
        val intent = Intent(this, PostLogin::class.java)
        startActivity(intent)
    }

    fun signup(view: View){
        val intent = Intent(this, LoginOptions::class.java)
        startActivity(intent)
    }

}