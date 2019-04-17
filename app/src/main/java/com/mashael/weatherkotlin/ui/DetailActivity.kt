package com.mashael.weatherkotlin.ui

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.mashael.weatherkotlin.R
import com.mashael.weatherkotlin.domain.Forecast
import com.mashael.weatherkotlin.domain.commands.RequestDayForecastCommand
import com.mashael.weatherkotlin.domain.commands.RequestForecastCommand
import com.mashael.weatherkotlin.extensions.textColor
import com.mashael.weatherkotlin.ui.utils.ToolbarManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_forecast.*
import kotlinx.android.synthetic.main.item_forecast.icon
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.text.DateFormat
import java.util.*

class DetailActivity : AppCompatActivity(),ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object{
        val ID="DetailsActivity:id"
        val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()

        toolbarTitle=intent.getStringExtra(CITY_NAME)
        enableHomeAsUp { onBackPressed() }

        
        doAsync { 
            val result = RequestDayForecastCommand(intent.getLongExtra(ID,-1)).execute()
            uiThread { bindForecast(result) }
        }
    }

    private fun bindForecast(forecast: Forecast)= with(forecast) {
        Picasso.get().load(iconUrl).into(icon)
        supportActionBar?.subtitle=date.toDateString(DateFormat.FULL)
        weatherDescription.text=description
        bindWeather(high to maxTemperatureDetail, low to minTemperatureDetail)
    }

    private fun bindWeather(vararg views: Pair<Int,TextView>)=views.forEach{
        it.second.text="${it.first.toString()}"
        it.second.textColor = color(when (it.first) {
            in -50..0 ->android.R.color.holo_red_dark
            in 0..15 ->android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })

    }
    public fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)
}

private fun Long.toDateString(dateFormat: Int=DateFormat.MEDIUM): String {
    val dateFormat= DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return dateFormat.format(this)
}
