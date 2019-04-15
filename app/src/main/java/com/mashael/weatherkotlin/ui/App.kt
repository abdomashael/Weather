package com.mashael.weatherkotlin.ui

import android.app.Application
import com.mashael.weatherkotlin.extensions.DelegatesExt

class App : Application() {
    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}