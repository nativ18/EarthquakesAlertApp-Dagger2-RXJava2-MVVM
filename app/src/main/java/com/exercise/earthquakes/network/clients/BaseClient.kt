package com.exercise.earthquakes.network.clients

import com.exercise.earthquakes.network.EarthquakeRequestParams
import com.exercise.earthquakes.ui.interfaces.OnEarthquakeRetrieved

/**
 * Created by nativlevy on 2/12/18.
 */
abstract class BaseClient {

    abstract fun release()

    abstract fun queryEarthquakes(params: EarthquakeRequestParams, onEarthquakeReceived: OnEarthquakeRetrieved)
}