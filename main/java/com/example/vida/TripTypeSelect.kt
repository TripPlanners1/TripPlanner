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
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class TripTypeSelect : AppCompatActivity() {

    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLastLocation: Location? = null
    
    private var arrivalDate:String?=null
    private var returnDate:String?=null
    private var cityName:String?=null
    private var jsonBody:String?=null
    private lateinit var preResponse:JSONObject
    private lateinit var firstResponse:JSONObject
    private lateinit var secondResponse:JSONObject
    private lateinit var arrivalSelect:TextView
    private lateinit var returnSelect:TextView

    private lateinit var selectionName:TextView
    private lateinit var selectionImage:ImageView
    private var locations:MutableList<Any> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trip_type_selection)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        
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

    public override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        }
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
        getLastLocation()

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
                            intent.putExtra("lat", mLastLocation?.latitude.toString())
                            intent.putExtra("lng", mLastLocation?.longitude.toString())
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

    fun goToNear(view: View){

        getLastLocation()

        var volleyRequestQueue: RequestQueue? = null
        var dialog: ProgressDialog? = null
        val serverAPIURL: String = "https://$serverID.ngrok.io/tripPlanner/seeNearest/"
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
                    secondResponse=responseObj
                    //val error = responseObj.get("errorClass")
                    if (status==200){
                        val intent = Intent(this, main_map_2::class.java)
                        intent.putExtra("locations",extractJson(secondResponse))
                        intent.putExtra("cityName",cityName)
                        intent.putExtra("lat", mLastLocation?.latitude.toString())
                        intent.putExtra("lng", mLastLocation?.longitude.toString())
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
                val jsonBody = JSONObject()
                jsonBody.put("latitude",mLastLocation?.latitude.toString())
                jsonBody.put("longitude",mLastLocation?.longitude.toString())
                return jsonBody.toString().toByteArray()
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
        json.remove("response")

        return json.toString()
    }




    private fun changeImage(){
        when(cityName){
            "Vienna" -> selectionImage.setImageResource(R.drawable.vienna)
            "Budapest" -> selectionImage.setImageResource(R.drawable.parliment)
            "Prague" -> selectionImage.setImageResource(R.drawable.prague)
        }
    }


    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     *
     *
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result
                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                }
            }
    }

    /**
     * Shows a [] using `text`.

     * @param text The Snackbar text.
     */
//    private fun showMessage(text: String) {
//        val container = findViewById<View>(R.id.)
//        if (container != null) {
//            Toast.makeText(this@TripTypeSelect, text, Toast.LENGTH_LONG).show()
//        }
//    }

    /**
     * Shows a [].

     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * *
     * @param actionStringId   The text of the action item.
     * *
     * @param listener         The listener associated with the Snackbar action.
     */
    private fun showSnackbar(mainTextStringId: Int, actionStringId: Int,
                             listener: View.OnClickListener) {

        Toast.makeText(this@TripTypeSelect, getString(mainTextStringId), Toast.LENGTH_LONG).show()
    }

    /**
     * Return the current state of the permissions needed.
     */
    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this@TripTypeSelect,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
            Manifest.permission.ACCESS_COARSE_LOCATION)

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                View.OnClickListener {
                    // Request permission
                    startLocationPermissionRequest()
                })

        } else {
            Log.i(TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest()
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation()
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                    View.OnClickListener {
                        // Build intent that displays the App settings screen.
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package",
                            BuildConfig.APPLICATION_ID, null)
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
            }
        }
    }

    companion object {

        private val TAG = "LocationProvider"

        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }



}