package com.example.vida

import android.app.ProgressDialog
import android.content.Intent
import android.icu.text.DateFormat.MEDIUM
import android.os.Bundle
import android.renderscript.RenderScript
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject
import java.lang.Exception
import java.text.DateFormat.MEDIUM




class MainActivity : AppCompatActivity() {
    private lateinit  var loginUserName : TextView
    private lateinit  var loginPassword : TextView

    private lateinit  var showPassword : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginUserName = findViewById(R.id.loginUsername) as TextView
        loginPassword = findViewById(R.id.loginPassword) as TextView

        showPassword = findViewById(R.id.showPassword) as ImageButton
        showHidePW()
    }

    fun showHidePW(){
        var isClicked = false
        showPassword.setOnClickListener {
            if (!isClicked) {
                // show password
                loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                isClicked=true
            } else {
                // hide password
                loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                isClicked=false
            }
        }
    }

    fun login(view: View){
        val intent = Intent(this, PostLogin::class.java)
        startActivity(intent)
    }

    fun signup(view: View){
        val intent = Intent(this, LoginOptions::class.java)
        startActivity(intent)
    }

    fun UserLogin(view: View){
            var userName : String = loginUserName.text.toString()
            var password : String = loginPassword.text.toString()

            var volleyRequestQueue: RequestQueue? = null
            var dialog: ProgressDialog? = null
            val serverAPIURL: String = "https://f2c9bb66d7c9.ngrok.io/tripPlanner/login/$userName/$password"
            val TAG = "Work"

            volleyRequestQueue = Volley.newRequestQueue(this)

            val parameters: MutableMap<String, String> = HashMap()

            val strReq: StringRequest = object : StringRequest(
                    Method.GET, serverAPIURL,
                    Response.Listener { response ->
                        Log.e(TAG, "map" + response)
                        //dialog?.dismiss()
                        var status : Int = 0
                        var userID : Int = 0
                        // Handle Server response here
                        try {
                            val responseObj = JSONObject(response)
                            status = responseObj.getInt("response")
                            userID = responseObj.getInt("userID")
                            //val error = responseObj.get("errorClass")
                            if (status==200){
                                val intent = Intent(this, PostLogin::class.java)
                                intent.putExtra("userName", userName);
                                intent.putExtra("userID", userID);
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "Sign in failed, check username or password", Toast.LENGTH_LONG).show()
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