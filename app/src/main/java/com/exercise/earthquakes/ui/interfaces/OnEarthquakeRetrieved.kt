package com.exercise.earthquakes.ui.interfaces

import com.exercise.earthquakes.EQApplication
import com.exercise.earthquakes.R
import com.exercise.earthquakes.model.Earthquakes

/**
 * Created by nativlevy on 2/11/18.
 */
interface OnEarthquakeRetrieved {

    fun onRetrieved(earthquakes: Earthquakes)

    fun onError(msg: String? = EQApplication.getApplication().getString(R.string.network_error))
}