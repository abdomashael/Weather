package com.mashael.weatherkotlin.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.mashael.weatherkotlin.ui.App
import org.jetbrains.anko.db.*

class ForecastDbHelper(ctx: Context = App.instance) :
    ManagedSQLiteOpenHelper(ctx,
        DB_NAME, null,
        DB_VERSION
    ) {

    infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)

    object CityForecastTable {
        const val NAME = "CityForecast"
        const val ID = "_id"
        const val CITY = "city"
        const val COUNTRY = "country"
    }

    object DayForecastTable {
        val NAME = "DayForecast"
        val ID = "_id"
        val DATE = "date"
        val DESCRIPTION = "description"
        val HIGH = "high"
        val LOW = "low"
        val ICON_URL = "iconUrl"
        val CITY_ID = "cityId"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            CityForecastTable.NAME, true,
            CityForecastTable.ID to INTEGER + PRIMARY_KEY,
            CityForecastTable.CITY to TEXT,
            CityForecastTable.COUNTRY to TEXT
        )

        db.createTable(
            DayForecastTable.NAME, true,
            DayForecastTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            DayForecastTable.DATE to INTEGER,
            DayForecastTable.DESCRIPTION to TEXT,
            DayForecastTable.HIGH to INTEGER,
            DayForecastTable.LOW to INTEGER,
            DayForecastTable.ICON_URL to TEXT,
            DayForecastTable.CITY_ID to INTEGER
        )


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(CityForecastTable.NAME, true)
        db.dropTable(DayForecastTable.NAME, true)
        onCreate(db)
    }

    companion object {
        val DB_NAME = "forecast.db"
        val DB_VERSION = 1
        val instance by lazy { ForecastDbHelper() }
    }

}