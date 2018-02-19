package com.exercise.earthquakes

import android.app.Activity
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability


/**
 * Created by nativlevy on 2/15/18.
 */
class PlayServicesUtils {

    companion object {
        private val PLAY_SERVICES_RESOLUTION_REQUEST = 1

        fun checkPlayServices(activity: Activity): Boolean {
            val gApi = GoogleApiAvailability.getInstance()
            val resultCode = gApi.isGooglePlayServicesAvailable(activity)
            if (resultCode == ConnectionResult.SUCCESS) {
                return true
            } else {
                if (gApi.isUserResolvableError(resultCode)) {
                    gApi.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)?.show()
                } else {
                    Toast.makeText(activity, activity.resources.getString(R.string.toast_playservices_missing), Toast.LENGTH_LONG).show()
                }
            }
            return false
        }
    }
}