package com.example.vida

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class TripTypeSelect : AppCompatActivity() {

    private var arrivalDate:String?=null
    private var returnDate:String?=null
    private var cityName:String?=null
    private var jsonBody:String?=null
    private lateinit var preResponse:JSONObject
    private lateinit var firstResponse:JSONObject
    private lateinit var arrivalSelect:TextView
    private lateinit var returnSelect:TextView

    private lateinit var selectionName:TextView
    private lateinit var selectionImage:ImageView
    private var locations:MutableList<Any> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_type_selection)


        arrivalSelect=findViewById(R.id.arrivalSelect)
        returnSelect=findViewById(R.id.returnSelect)

        arrivalDate=intent.getStringExtra("arrivalDate")
        returnDate=intent.getStringExtra("returnDate")
        cityName = intent.getStringExtra("cityName")

        jsonBody=intent.getStringExtra("stringJson")
        preResponse= JSONObject(jsonBody)
        preResponse.remove("response")

        putDates()

        selectionImage = findViewById(R.id.selectionImage)
        selectionName = findViewById(R.id.selectionName)

        selectionName.text = cityName

        changeImage()

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

        var volleyRequestQueue: RequestQueue? = null
        var dialog: ProgressDialog? = null
        val serverAPIURL: String = "https://$serverID.ngrok.io/tripPlanner/seeoneplan/"
        val TAG = "Work"

        volleyRequestQueue = Volley.newRequestQueue(this)


        val strReq: StringRequest = object : StringRequest(
                Method.POST, serverAPIURL,
                Response.Listener { response ->
                    Log.e(TAG, "map" + response)
                    var status : Int = 0
                    // Handle Server response here
                    try {
                        val responseObj = JSONObject(response)
                        status = responseObj.getInt("response")
                        firstResponse=responseObj
                        //val error = responseObj.get("errorClass")
                        if (status==200){
                            val intent = Intent(this, main_map::class.java)
                            intent.putExtra("locations",extractJson(firstResponse))
                            intent.putExtra("cityName",cityName)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "Operation failed, check dates", Toast.LENGTH_LONG).show()
                        }

                    } catch (e: Exception) { // caught while parsing the response
                        Log.e(TAG, "problem occurred")
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { volleyError -> // error occurred
                    Log.e(TAG, "problem occurred, volley error: " + volleyError.message)
                }) {
            override fun getBodyContentType(): String? {
                return "text/plain"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return preResponse.toString().toByteArray()
            }


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {

                val headers: MutableMap<String, String> = HashMap()
                // Add your Header paramters here
                return headers
            }
        }
        // Adding request to request queue
        volleyRequestQueue?.add(strReq)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun putDates(){
        arrivalSelect.text=arrivalDate.toString()
        val frDate = "- "+returnDate.toString()
        returnSelect.text=frDate
    }

    fun extractJson(json:JSONObject):String{
        val string="{\n" +
                "   \"0\":{\n" +
                "      \"0\":[\n" +
                "         2,\n" +
                "         \"BB'z Bar & Grill\",\n" +
                "         \"Budapest, 1013 Hungary\",\n" +
                "         \"bbzbar.com\",\n" +
                "         4.400000095367432\n" +
                "      ],\n" +
                "      \"1\":[\n" +
                "         2,\n" +
                "         \"Rakoczi Restaurant\",\n" +
                "         \"Budapest, Rakoczi ter 9, 1084 Hungary\",\n" +
                "         \"rakoczietterem.hu\",\n" +
                "         4.300000190734863\n" +
                "      ]\n" +
                "   },\n" +
                "   \"1\":{\n" +
                "      \"0\":[\n" +
                "         2,\n" +
                "         \"Backyard\",\n" +
                "         \"Budapest, Kazinczy u. 47, 1075 Hungary\",\n" +
                "         \"facebook.com\",\n" +
                "         4.400000095367432\n" +
                "      ],\n" +
                "      \"1\":[\n" +
                "         2,\n" +
                "         \"Tereza Mexican Restaurant\",\n" +
                "         \"Budapest, Nagymezo u. 3, 1065 Hungary\",\n" +
                "         \"tereza.hu\",\n" +
                "         4.199999809265137\n" +
                "      ]\n" +
                "   }\n" +
                "}"
        json.remove("response")

        return json.toString()
//        for (i in 0 .. json.length()-1) {
//            for (j in 0 .. json.getJSONObject(i.toString()).length()-1) {
//                val array=json.getJSONObject(i.toString()).getJSONArray(j.toString())
////                for (k in 0 until array.length()) {
////                    location.add(array[k])
////                }
//                locations.add(array)
//            }
//            //locations.add(location)
//        }

        //println("HEEEEEEY MOFO HERE----------->"+locations[2])

//        val intent = Intent(this, main_map::class.java)
//        intent.putExtra("locations", json.toString())
//        startActivity(intent)
    }




    fun changeImage(){
        when(cityName){
            "Vienna" -> selectionImage.setImageResource(R.drawable.vienna)
            "Budapest" -> selectionImage.setImageResource(R.drawable.parliment)
            "Prague" -> selectionImage.setImageResource(R.drawable.prague)
        }
    }







}