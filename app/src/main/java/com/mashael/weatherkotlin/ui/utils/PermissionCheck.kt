package com.mashael.weatherkotlin.ui.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

class PermissionCheck(val mActivity: Activity) {
    fun checkLocationPermission():Boolean{
        return ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}