package com.mashael.weatherkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mashael.weatherkotlin.utils.ctx
import com.mashael.weatherkotlin.domain.Forecast
import com.mashael.weatherkotlin.domain.ForecastList
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class ForecastListAdapter(val weekForecast: ForecastList, val itemClick: (Forecast)->Unit) :
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {


        class ViewHolder(view: View, val itemClick: (Forecast)->Unit) : RecyclerView.ViewHolder(view) {
        private val iconView = view.find<ImageView>(R.id.icon)
        private val dateView = view.find<TextView>(R.id.date)
        private val descriptionView = view.find<TextView>(R.id.description)
        private val maxTemperatureView = view.find<TextView>(R.id.maxTemperature)
        private val minTemperatureView = view.find<TextView>(R.id.minTemperature)

        fun bindForecast(forcast: Forecast) {
            with(forcast) {
                Picasso.with(itemView.ctx).load(iconUrl).into(iconView)
                dateView.text = date
                descriptionView.text = description
                maxTemperatureView.text = "$high"
                minTemperatureView.text = "$low"
                itemView.setOnClickListener { itemClick(this) }

            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ForecastListAdapter.ViewHolder {
        val view = LayoutInflater.from(p0.ctx).inflate(R.layout.item_forecast, p0, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return weekForecast.size
    }

    override fun onBindViewHolder(p0: ForecastListAdapter.ViewHolder, p1: Int) {
        p0.bindForecast(weekForecast[p1])
    }


}