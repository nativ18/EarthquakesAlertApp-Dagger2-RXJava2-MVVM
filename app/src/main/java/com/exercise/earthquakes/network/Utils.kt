package com.exercise.earthquakes.network

import android.content.Context
import android.net.ConnectivityManager
import com.exercise.earthquakes.EQApplication

/**
 * Created by nativlevy on 2/15/18.
 */
class Utils {

    companion object {

         fun hasNetwork(): Boolean {
            val cm = EQApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val netInfo = cm.activeNetworkInfo
            //should check null because in air plan mode it will be null
            return netInfo != null && netInfo.isConnected
        }
    }

}