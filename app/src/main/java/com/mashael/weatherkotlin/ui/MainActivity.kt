package com.mashael.weatherkotlin.ui

import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mashael.weatherkotlin.R
import com.mashael.weatherkotlin.domain.commands.RequestCurrentForecastCommand
import com.mashael.weatherkotlin.ui.utils.PermissionCheck
import com.mashael.weatherkotlin.domain.commands.RequestForecastCommand
import com.mashael.weatherkotlin.extensions.DelegatesExt
import com.mashael.weatherkotlin.ui.utils.ToolbarManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.current_forecast.*
import org.jetbrains.anko.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


private val ZIP_NO = "94043"


class MainActivity : AppCompatActivity(), ToolbarManager {
    /*val url = "http://api.openweathermap.org/data/2.5/forecast/daily?" +
            "APPID=15646a06818f61f7b8d7823ca833e1ce&zip=94043&mode=json&units=metric&cnt=7"
*/

    companion object {
        val sdf = SimpleDateFormat("HH:mm")
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    val cityCoordinatesLat: String by DelegatesExt.preference(
        this, SettingsActivity.COORDINATES_LAT,
        SettingsActivity.DEFUALT_COORDINATES_LAT
    )

    val cityCoordinatesLon: String by DelegatesExt.preference(
        this, SettingsActivity.COORDINATES_LON,
        SettingsActivity.DEFUALT_COORDINATES_LON
    )

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        forecastList.layoutManager = LinearLayoutManager(this)
        //forecastList.adapter = ForecastListAdapter(items)
        getCityCoordinates()

    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() {
        val cityCoordinates = "&lat=$cityCoordinatesLat&lon=$cityCoordinatesLon"
        forecastListRequest(cityCoordinates)
        currentForecastRequest(cityCoordinates)

    }

    private fun getCityCoordinates() {
        val permissionCheck = PermissionCheck(this)
        if (permissionCheck.checkLocationPermission()) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    val cityCoordinates = "&lat=" + location?.latitude + "&lon=" + location?.longitude
                }

        }

    }

    private fun forecastListRequest(cityCoordinates: String) {
        doAsync {
            val result = RequestForecastCommand(cityCoordinates).execute()
            uiThread {
                val adapter = ForecastListAdapter(result) {
                    startActivity<DetailActivity>(
                        DetailActivity.ID to it.id,
                        DetailActivity.CITY_NAME to result.city
                    )
                }
                forecastList.adapter = adapter
                citynameTextview.text = result.city
                countrynameTextview.text = result.country
                toolbarTitle = "${result.city} (${result.country})"
            }
        }
    }

    private fun currentForecastRequest(cityCoordinates: String) {
        doAsync {
            val result = RequestCurrentForecastCommand(cityCoordinates).execute()
            uiThread {
                with(result) {
                    currentMainTextView.text = main
                    currentDescriptionTextView.text = description
                    currentTempTextView.text = "$temp °c"
                    Picasso.get().load(iconUrl).into(currentIconImageview)
                    humidityTextView.text = "$humidity %"
                    pressureTextView.text = "$pressure hPa"
                    windSpeedTextView.text = "$speed m/sec"
                }
                timeTextView.text = sdf.format(Date())
            }
        }
    }
}
