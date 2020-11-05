package com.example.vida

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
/*import okhttp3.Call
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody*/
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun login(view: View){
        val intent = Intent(this, PostLogin::class.java)
        startActivity(intent)
    }

    fun signup(view: View){
        val intent = Intent(this, LoginOptions::class.java)
        startActivity(intent)
    }

    fun sendPostRequest(view:View) {
        try {
            //var reqParam = URLEncoder.encode("?name", "UTF-8") + "=" + URLEncoder.encode("Testz", "UTF-8")
            val mURL = URL("https://e101ebf8cb03.ngrok.io/tripPlanner/hello_world?name=Testz")

            with(mURL.openConnection() as HttpURLConnection) {
                // optional default is GET
                requestMethod = "POST"

                /*val wr = OutputStreamWriter(getOutputStream());
                wr.write(reqParam);
                wr.flush();*/

                println("URL : $url")
                println("Response Code : $responseCode")

                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    println("Response : $response")
                }
            }
        }catch(ex:Exception){
            println(ex.message)
        }
        }

}