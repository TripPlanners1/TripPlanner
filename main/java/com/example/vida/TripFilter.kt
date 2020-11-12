package com.example.vida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import java.lang.Exception

class TripFilter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_filter)

        val dropdown : Spinner = findViewById(R.id.spinner)
        var items : ArrayList<String> = ArrayList<String>(3)
        items.add("Historical")
        items.add("Boat Riding")
        items.add("Hiking")

        val adapter = ArrayAdapter<String>(this, R.layout.dropdown_row, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdown.setAdapter(adapter)

    }
    fun backToTS(view: View){
        val intent = Intent(this, TripTypeSelect::class.java)
        startActivity(intent)
    }
}

