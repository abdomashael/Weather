package com.mashael.weatherkotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mashael.weatherkotlin.R
import com.mashael.weatherkotlin.extensions.DelegatesExt
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        val COORDINATES_LAT = "coordinatesLat"
        val DEFUALT_COORDINATES_LAT = "30.044420"
        val COORDINATES_LON = "coordinatesLon"
        val DEFUALT_COORDINATES_LON = "31.235712"
    }

    var cityCoordinatesLat: String by DelegatesExt.preference(this, COORDINATES_LAT, DEFUALT_COORDINATES_LAT)

    var cityCoordinatesLon: String by DelegatesExt.preference(this, COORDINATES_LON, DEFUALT_COORDINATES_LON)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cityLat.setText(cityCoordinatesLat)
        cityLon.setText(cityCoordinatesLon)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cityCoordinatesLat = cityLat.text.toString()
        cityCoordinatesLon = cityLon.text.toString()
    }
}
