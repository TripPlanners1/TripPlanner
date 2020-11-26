package com.example.vida

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.util.*

class PostLogin : AppCompatActivity() {
    private lateinit var textView : TextView
    private lateinit var arrivalDate : EditText
    private lateinit var returnDate : EditText
    private lateinit var arrivalButton : ImageButton
    private lateinit var returnButton : ImageButton

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_login)

        textView = findViewById(R.id.postLoginText)
        val userName = intent.getStringExtra("userName")
        val welcome = "Welcome, "+ userName
        textView.text = welcome

        arrangeDates()
    }

    fun seePlans(view: View){
        val intent = Intent(this, TripTypeSelect::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun arrangeDates(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        arrivalDate = findViewById(R.id.arrivalDate)
        returnDate = findViewById(R.id.returnDate)

        arrivalButton = findViewById(R.id.arrivalButton)
        returnButton = findViewById(R.id.returnButton)

        arrivalButton.setOnClickListener {
                val dataPicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                val AD = "$dayOfMonth/$month/$year"
                arrivalDate.setText(AD)
            }, year, month, day)
            dataPicker.show()
        }


        returnButton.setOnClickListener {
            val dataPicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                val RD = "$dayOfMonth/$month/$year"
                returnDate.setText(RD)
            }, year, month, day)
            dataPicker.show()
        }
    }

}