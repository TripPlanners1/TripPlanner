package com.example.vida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PostLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_login)
    }

    fun seePlans(view: View){
        val intent = Intent(this, TripTypeSelect::class.java)
        startActivity(intent)
    }
}