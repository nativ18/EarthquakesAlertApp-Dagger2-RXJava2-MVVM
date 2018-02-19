package com.exercise.earthquakes.ui

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.exercise.earthquakes.PlayServicesUtils
import com.exercise.earthquakes.R
import com.exercise.earthquakes.extensions.launchActivity
import com.exercise.earthquakes.model.EarthquakeItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by nativlevy on 2/11/18.
 */
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        val EARTHQUAKE_LIST = "earthquake_list"
        val FOCUSED_EARTHQUAKE = "focused_earthquake"

        fun startActivity(activity: Activity, bundle: Bundle) {
            val hasPlayServices = PlayServicesUtils.checkPlayServices(activity)
            if (hasPlayServices)
                activity.launchActivity<MapActivity>(bundle)
        }
    }

    private lateinit var mMap: GoogleMap
    private var earthquakes: ArrayList<EarthquakeItem>? = null
    private var focusedEarthquake: EarthquakeItem? = null
    private var mMapFragment: SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mMapFragment?.getMapAsync(this)

        // makes the internal map view to load faster
        mMapFragment?.onEnterAmbient(null)

        val bundle = intent.extras
        if (bundle != null) {
            if (bundle.containsKey(EARTHQUAKE_LIST))
                earthquakes = bundle.getSerializable(EARTHQUAKE_LIST) as ArrayList<EarthquakeItem>

            if (bundle.containsKey(FOCUSED_EARTHQUAKE))
                focusedEarthquake = bundle.getSerializable(FOCUSED_EARTHQUAKE) as EarthquakeItem
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        addEarthquakesMarkers()
    }

    private fun addEarthquakesMarkers() {
        earthquakes?.filter { item -> item.lat != null && item.lng != null }?.forEachIndexed { index, item ->
            val latLng = LatLng(item.lat!!, item.lng!!)
            addMarket(latLng, focusedEarthquake == null && index == earthquakes!!.size - 1)
        }

        if (focusedEarthquake?.lat != null && focusedEarthquake?.lng != null) {
            addMarket(LatLng(focusedEarthquake?.lat!!, focusedEarthquake?.lng!!), true)
        }
    }

    private fun addMarket(latLng: LatLng, moveCamera: Boolean) {
        mMap.addMarker(MarkerOptions().position(latLng))
        if (moveCamera)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }
}
