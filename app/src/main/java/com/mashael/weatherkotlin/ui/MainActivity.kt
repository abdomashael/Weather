package com.mashael.weatherkotlin.ui

import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mashael.weatherkotlin.R
import com.mashael.weatherkotlin.domain.RequestCurrentForecastCommand
import com.mashael.weatherkotlin.ui.utils.PermissionCheck
import com.mashael.weatherkotlin.domain.RequestForecastCommand
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.current_forecast.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread


private val ZIP_NO = "94043"
/*
private val items = listOf<String>(
    "Mon 6/23 - Suuny - 31/17",
    "Tue 6/24 - Foggy - 21/8",
    "Wed 6/25 - Cloudy - 22/17",
    "Thurs 6/26 - Rainy - 31/17",
    "Fri 6/27 - Foggy - 21/10",
    "Sat 6/28 - TRAPPED IN WEATHER STATION - 23/18",
    "Sun 6/29 - Suuny - 20/7"
)
*/

class MainActivity : AppCompatActivity() {
    /*val url = "http://api.openweathermap.org/data/2.5/forecast/daily?" +
            "APPID=15646a06818f61f7b8d7823ca833e1ce&zip=94043&mode=json&units=metric&cnt=7"
*/
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        forecastList.layoutManager = LinearLayoutManager(this)
        //forecastList.adapter = ForecastListAdapter(items)
        getCityCoordinates( forecastList)

    }

    private fun getCityCoordinates(forecastList: RecyclerView) {
        val permissionCheck = PermissionCheck(this)
        if (permissionCheck.checkLocationPermission()) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    val cityCoordinates = "&lat=" + location?.latitude + "&lon=" + location?.longitude
                    currentForecastRequest(cityCoordinates)
                    forecastListRequest(cityCoordinates)
                }

        }

    }

    private fun forecastListRequest( cityCoordinates: String) {
        doAsync {
            val result = RequestForecastCommand(cityCoordinates).execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter(result) { forecast ->
                    toast(forecast.date)
                }

                citynameTextview.text = result.city
                countrynameTextview.text = result.country
            }
        }
    }
    private fun currentForecastRequest( cityCoordinates: String) {
        doAsync {
            val result = RequestCurrentForecastCommand(cityCoordinates).execute()
            uiThread {
                with(result){
                    currentMainTextView.text=main
                    currentDescriptionTextView.text=description
                    currentTempTextView.text="$temp °c"
                    Picasso.get().load(iconUrl).into(currentIconImageview)
                    humidityTextView.text="$humidity %"
                    pressureTextView.text="$pressure hPa"
                    windSpeedTextView.text="$speed m/sec"
                }
            }
        }
    }
}
