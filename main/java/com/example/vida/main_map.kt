package com.example.vida

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class main_map : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val budaCastle = LatLng(47.490998036 , 19.037166518)
        /*val center:CameraUpdate=CameraUpdateFactory.newLatLng(budapest)
        val zoom:CameraUpdate=CameraUpdateFactory.zoomTo(8.0F)
        mMap.moveCamera(center)
        mMap.animateCamera(zoom)*/
        val cameraPosition = CameraPosition.Builder()
            .target(budaCastle)
            .zoom(14f)
            .bearing(0f)
            .tilt(30f)
            .build()

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        val markedPlaces=ArrayList<LatLng>()
        mMap.setOnMapClickListener {
            markedPlaces.add(it)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it))
        }
    }
    fun openDesc(view: View){
        val intent = Intent(this, LocationDesc::class.java)
        startActivity(intent)
    }
}