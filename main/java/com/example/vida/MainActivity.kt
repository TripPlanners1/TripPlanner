package com.example.vida

import RestApiService
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName


data class UserInfo (
        @SerializedName("user_id") val userId: Int?,
        @SerializedName("user_name") val userName: String?,
        @SerializedName("user_email") val userEmail: String?,
        @SerializedName("user_age") val userAge: String?,
        @SerializedName("user_uid") val userUid: String?
)

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


    fun addDummyUser(view: View) {
        val apiService = RestApiService()
        val userInfo = UserInfo(  userId = null,
                userName = "Alex",
                userEmail = "alex@gmail.com",
                userAge = "32",
                userUid = "164E92FC-D37A-4946-81CB-29DE7EE4B124" )

        apiService.addUser(userInfo) {
            if (it?.userId != null) {
                 // = newly added user parsed as response
                 //it?.userId = newly added user ID
            } else {
                Log.v("YO","ERRROOOOR")
            }
        }
    }

}