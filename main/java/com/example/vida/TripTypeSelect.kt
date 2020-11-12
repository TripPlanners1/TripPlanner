package com.example.vida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner



class TripTypeSelect : AppCompatActivity() {
    private lateinit var list : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_type_selection)

        /* list = findViewById<ListView>(R.id.trip_list)
         val listItems = ArrayList<TripListItem>
     */
    }


    fun filters(view: View){
        val intent = Intent(this, TripFilter::class.java)
        startActivity(intent)
    }
    fun backToPostLogin(view: View){
        val intent = Intent(this, PostLogin::class.java)
        startActivity(intent)
    }
    fun goToMap(view: View){
        val intent = Intent(this, main_map::class.java)
        startActivity(intent)
    }
}