package com.exercise.earthquakes.ui.databinding

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.exercise.earthquakes.model.EarthquakeItem
import com.exercise.earthquakes.ui.MapActivity

/**
 * Created by nativlevy on 2/11/18.
 */
class EarthquakeItemActions {

    fun openMapView(v: View, eq: EarthquakeItem) {
        val bundle = Bundle()
        bundle.putSerializable(MapActivity.FOCUSED_EARTHQUAKE, eq)
        MapActivity.startActivity(v.context as Activity, bundle)
    }
}