package com.example.vida

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import org.json.JSONObject


class main_map : AppCompatActivity(), OnMapReadyCallback {
    private var cityName:String?=null
    private lateinit var mapTitle: ImageView

    private lateinit var mMap: GoogleMap

    private lateinit var json: JSONObject

    private var locations:MutableList<Any> = mutableListOf()
    private var markers:MutableList<LatLng?> = mutableListOf()
    private var locationList:MutableList<Location> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapTitle = findViewById(R.id.mapTitle)
        changeImage()


        val string = intent.getStringExtra("locations")
        cityName = intent.getStringExtra("cityName")
        json = JSONObject(string)

        extractJSON(json)

        var listView = findViewById<ListView>(R.id.listView)

        listView.adapter = LocationAdapter(this,R.layout.tts_list_item,locationList)

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            animateZoom(position)
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val markedPlaces=ArrayList<LatLng?>()

        for (item in markers){
            markedPlaces.add(item)
            mMap.addMarker(item?.let { MarkerOptions().position(it) })
        }

        var center : LatLng? = null
        when(cityName){
            "Vienna" -> center = LatLng(48.20849 , 16.37208)
            "Budapest" -> center = LatLng(47.490998036 , 19.037166518)
            "Prague" -> center = LatLng(50.073658 , 14.418540)
        }

        val cameraPosition = CameraPosition.Builder()
                .target(center)
                .zoom(12f)
                .bearing(0f)
                .tilt(30f)
                .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

//        mMap.setOnMapClickListener {
//            markedPlaces.add(it)
//            mMap.clear()
//            mMap.addMarker(MarkerOptions().position(it))
//        }
    }

    fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        animateZoom(position)
    }

    fun animateZoom(position:Int) {
        val cameraPosition = CameraPosition.Builder()
                .target(getLocationFromAddress(this, locationList[position].address))
                .zoom(14f)
                .bearing(0f)
                .tilt(30f)
                .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            location.getLatitude()
            location.getLongitude()
            p1 = LatLng(location.getLatitude(), location.getLongitude())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return p1
    }

    private fun changeImage(){
        when(cityName){
            "Vienna" -> mapTitle.setImageResource(R.drawable.vienna)
            "Budapest" -> mapTitle.setImageResource(R.drawable.parliment)
            "Prague" -> mapTitle.setImageResource(R.drawable.prague)
        }
    }

    fun extractJSON(json: JSONObject){
        for (i in 0 .. json.length()-1) {
            for (j in 0 .. json.getJSONObject(i.toString()).length()-1) {
                val array=json.getJSONObject(i.toString()).getJSONArray(j.toString())
                markers.add(getLocationFromAddress(this,array[2].toString()))
                val loc = Location(array[1].toString(),array[2].toString(),array[3].toString(),array[4].toString().toDouble(),"Day "+(i+1))
                locationList.add(loc)
                locations.add(array)
            }
        }
    }


}