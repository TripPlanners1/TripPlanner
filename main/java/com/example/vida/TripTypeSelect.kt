package com.example.vida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner

class TripTypeSelect : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_type_selection)
    }

    fun filters(view: View){
        val intent = Intent(this, TripFilter::class.java)
        startActivity(intent)
    }
    fun backToPostLogin(view: View){
        val intent = Intent(this, PostLogin::class.java)
        startActivity(intent)
    }
}