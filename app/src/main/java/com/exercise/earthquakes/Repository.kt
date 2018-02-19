package com.exercise.earthquakes

import com.exercise.earthquakes.model.Earthquakes
import com.exercise.earthquakes.network.EarthquakeRequestParams
import com.exercise.earthquakes.network.clients.BaseClient
import com.exercise.earthquakes.ui.interfaces.OnEarthquakeRetrieved
import javax.inject.Inject

/**
 * Created by nativlevy on 2/15/18.
 */
class Repository {

    var mEarthQuakes: Earthquakes? = null

    @Inject
    lateinit var mEarthquakeClient: BaseClient

    @Inject
    lateinit var mEarthquakeRequestParams: EarthquakeRequestParams

    fun reset() {
        mEarthQuakes = null
    }

    fun retrieveEarthquakes(onEarthquakeRetrieved: OnEarthquakeRetrieved) {
        if (mEarthQuakes != null) {
            onEarthquakeRetrieved.onRetrieved(mEarthQuakes!!)
        } else {
            mEarthquakeClient.queryEarthquakes(mEarthquakeRequestParams, object : OnEarthquakeRetrieved {
                override fun onRetrieved(earthquakes: Earthquakes) {
                    mEarthQuakes = earthquakes
                    onEarthquakeRetrieved.onRetrieved(earthquakes)
                }

                override fun onError(msg: String?) {
                    onEarthquakeRetrieved.onError(msg)
                }

            })
        }
    }

}