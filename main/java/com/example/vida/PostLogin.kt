package com.example.vida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class PostLogin : AppCompatActivity() {
    private lateinit var textView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_login)
        textView = findViewById(R.id.postLoginText)
        val userName = intent.getStringExtra("userName")
        val welcome = "Welcome, "+ userName
        textView.text = welcome
    }

    fun seePlans(view: View){
        val intent = Intent(this, TripTypeSelect::class.java)
        startActivity(intent)
    }
}