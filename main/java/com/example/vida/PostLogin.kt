package com.example.vida

//import com.android.volley.Request.Method.GET
//import com.androidnetworking.common.Method.GET
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.*


class PostLogin : AppCompatActivity() {
    private var userID : Int = 0
    private lateinit var textView : TextView
    private lateinit var arrivalDate : EditText
    private lateinit var returnDate : EditText
    private lateinit var arrivalButton : ImageButton
    private lateinit var returnButton : ImageButton
    private var AD:String?=null
    private var RD:String?=null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_login)

        textView = findViewById(R.id.postLoginText)
        val userName = intent.getStringExtra("userName")
        val welcome = "Welcome, "+ userName
        textView.text = welcome

        userID = intent.getIntExtra("userID",0)

        arrangeDates()
    }

    fun seePlans(view: View){
        val cityName = "Budapest"
        val arrivalD : String = arrivalDate.text.toString()
        val returnD : String = returnDate.text.toString()
        /*val data =JSONObject()
        data.put("cityName","Budapest")
        data.put("dateOfArrival",arrivalD)
        data.put("dateOfReturn",returnD)
        data.put("userID",userID)*/


        var volleyRequestQueue: RequestQueue? = null
        var dialog: ProgressDialog? = null
        //val serverAPIURL: String = "https://7e493076bb13.ngrok.io/tripPlanner/seePlans/$cityName/$arrivalD/$returnD/$userID"
        val serverAPIURL: String = "https://f2c9bb66d7c9.ngrok.io/tripPlanner/seePlans/"
        val TAG = "Work"

        volleyRequestQueue = Volley.newRequestQueue(this)


        /*val parameters: MutableMap<String, String> = HashMap()
        parameters.put("cityName",cityName)
        parameters.put("dateOfArrival",arrivalD)
        parameters.put("dateOfReturn",returnD)
        parameters.put("userID",userID.toString())
        val string = "Hello testing something"
        val request_json = JsonObjectRequest(serverAPIURL, string,
                Response.Listener {response ->
                    Log.e(TAG, "map" + response)
                    var status : Int = 0
                    // Handle Server response here
                    try {
                        status = response.getInt("response")
                        //val error = responseObj.get("errorClass")
                        if (status==200){
                            val intent = Intent(this, TripTypeSelect::class.java)
                            intent.putExtra("arrivalDate",AD)
                            intent.putExtra("returnDate",RD)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, "Operation failed, check dates", Toast.LENGTH_LONG).show()
                        }

                    } catch (e: Exception) { // caught while parsing the response
                        Log.e(TAG, "problem occurred")
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { error -> VolleyLog.e("Error: ", error.message) })
*/

        val strReq: StringRequest = object : StringRequest(
                Method.POST, serverAPIURL,
                Response.Listener { response ->
                    Log.e(TAG, "map" + response)
                    var status : Int = 0
                    var stringJson : String? = null
                    // Handle Server response here
                    try {
                        val responseObj = JSONObject(response)
                        status = responseObj.getInt("response")
                        stringJson = responseObj.toString()
                        //val error = responseObj.get("errorClass")
                        if (status==200){
                            val intent = Intent(this, TripTypeSelect::class.java)
                            intent.putExtra("arrivalDate",AD)
                            intent.putExtra("returnDate",RD)
                            intent.putExtra("stringJson",stringJson)
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
                jsonBody.put("cityName","Budapest")
                jsonBody.put("dateOfArrival",arrivalD)
                jsonBody.put("dateOfReturn",returnD)
                jsonBody.put("userID",userID)
                return jsonBody.toString().toByteArray()
            }
            /*override fun getParams(): MutableMap<String, String> {
                return parameters;
            }*/

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
                val cMonth = monthOfYear+1
                var fMonth:String=cMonth.toString()
                var fDay:String=dayOfMonth.toString()
                if (cMonth<10) {
                    fMonth = "0$cMonth"
                }
                if (dayOfMonth<10) {
                    fDay = "0$dayOfMonth"
                }
                 AD = "$fDay.$fMonth.$year"
                arrivalDate.setText(AD)
            },year,month,day)
            dataPicker.show()
        }


        returnButton.setOnClickListener {
            val dataPicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                val cMonth = monthOfYear+1
                var fMonth:String=cMonth.toString()
                var fDay:String=dayOfMonth.toString()
                if (cMonth<10) {
                    fMonth = "0$cMonth"
                }
                if (dayOfMonth<10) {
                    fDay = "0$dayOfMonth"
                }
                RD = "$fDay.$fMonth.$year"
                returnDate.setText(RD)
            }, year, month, day)
            dataPicker.show()
        }
    }

}