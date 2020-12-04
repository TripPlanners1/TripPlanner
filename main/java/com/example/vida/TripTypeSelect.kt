package com.example.vida

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class TripTypeSelect : AppCompatActivity() {

    private var arrivalDate:String?=null
    private var returnDate:String?=null
    private var jsonBody:String?=null
    private lateinit var preResponse:JSONObject
    private lateinit var firstResponse:JSONObject
    private lateinit var arrivalSelect:TextView
    private lateinit var returnSelect:TextView
    private lateinit var location : ArrayList<String>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_type_selection)

        arrivalSelect=findViewById(R.id.arrivalSelect)
        returnSelect=findViewById(R.id.returnSelect)

        arrivalDate=intent.getStringExtra("arrivalDate")
        returnDate=intent.getStringExtra("returnDate")

        jsonBody=intent.getStringExtra("stringJson")
        preResponse= JSONObject(jsonBody)
        preResponse.remove("response")
        //val n:JSONObject=response.remove("response")

        putDates()

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
        //val serverAPIURL: String = "https://7e493076bb13.ngrok.io/tripPlanner/seePlans/$cityName/$arrivalD/$returnD/$userID"
        val serverAPIURL: String = "https://f2c9bb66d7c9.ngrok.io/tripPlanner/seeoneplan/"
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
                            intent.putStringArrayListExtra("location",extractJson(firstResponse))
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

    fun extractJson(json: JSONObject):ArrayList<String>{
        json.remove("response")
        val array=json.getJSONObject("0").getJSONArray("0")
        println("array")
        for (i in 0 until array.length()) {
            location.add(i,array.getString(i))
        }

        println(location)
        return location
    }












}