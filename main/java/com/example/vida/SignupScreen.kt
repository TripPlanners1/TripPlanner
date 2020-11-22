package com.example.vida

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.androidnetworking.AndroidNetworking
import org.json.JSONObject
import java.lang.Exception

class SignupScreen : AppCompatActivity() {
    private lateinit var signUserName : TextView
    private lateinit var signEmail : TextView
    private lateinit var signPassword : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_screen)
        signUserName = findViewById(R.id.signUsername) as TextView
        signEmail = findViewById(R.id.signEmail) as TextView
        signPassword = findViewById(R.id.signPassword) as TextView
    }
    fun home(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun addUser(view: View){
        var userName : String = signUserName.text.toString()
        var email : String = signEmail.text.toString()
        var password : String = signPassword.text.toString()

        var volleyRequestQueue: RequestQueue? = null
        var dialog: ProgressDialog? = null
        val serverAPIURL: String = "https://fff9fe51134a.ngrok.io/tripPlanner/signup?nickname=$userName&password=$password&email=$email"
        val TAG = "Please Work"

        volleyRequestQueue = Volley.newRequestQueue(this)
        //dialog = ProgressDialog.show(this, "", "Please wait...", true);
        val parameters: MutableMap<String, String> = HashMap()
        // Add your parameters in HashMap
        //parameters.put("nickname", "marmenaid_");
        //parameters.put("password", "1234");


        val strReq: StringRequest = object : StringRequest(
                Method.GET, serverAPIURL,
                Response.Listener { response ->
                    Log.e(TAG, "map" + response)
                    //dialog?.dismiss()
                    var status : Int? = null
                    // Handle Server response here
                    try {
                        val responseObj = JSONObject(response)
                        val response = responseObj.getJSONObject("map")
                        status = response.getInt("response")

                        //val error = responseObj.get("errorClass")
                        if (status==200){
                            Toast.makeText(this, "Successful Sign Up", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this, "Sign up failed, check username or password", Toast.LENGTH_LONG).show()
                        }
                        //Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                    } catch (e: Exception) { // caught while parsing the response
                        Log.e(TAG, "problem occurred")
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { volleyError -> // error occurred
                    Log.e(TAG, "problem occurred, volley error: " + volleyError.message)
                }) {

            override fun getParams(): MutableMap<String, String> {
                return parameters;
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
}