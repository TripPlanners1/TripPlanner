package com.example.vida

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class LocationAdapter(context: Context,var resource:Int,var items:List<Location>):ArrayAdapter<Location>(context,resource,items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater:LayoutInflater = LayoutInflater.from(context)

        val view:View = layoutInflater.inflate(resource,null)

        val locationName:TextView=view.findViewById(R.id.locationName)
        val locationAdr:TextView=view.findViewById(R.id.locationAdr)
        val locationURL:TextView=view.findViewById(R.id.locationURL)
        val locationRating:TextView=view.findViewById(R.id.locationRating)
        val dayLabel:TextView=view.findViewById(R.id.dayLabel)

        var item:Location = items[position]
        locationName.text=item.name
        locationAdr.text=item.address
        locationURL.text=item.website
        locationRating.text= String.format("%.1f",item.rating)
        dayLabel.text= item.label


        return view
    }

}