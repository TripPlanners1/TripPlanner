package com.example.vida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class PostLogin : AppCompatActivity() {
    val textView : TextView = findViewById(R.id.postLoginText)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_login)
        val userName = intent.getStringExtra("userName")
        textView.text = userName
    }

    fun seePlans(view: View){
        val intent = Intent(this, TripTypeSelect::class.java)
        startActivity(intent)
    }
}