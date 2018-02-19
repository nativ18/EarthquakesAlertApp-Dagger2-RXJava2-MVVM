package com.exercise.earthquakes.ui.viewmodel

import com.exercise.earthquakes.Repository
import com.exercise.earthquakes.ui.di.DaggerModelViewDataComponent
import com.exercise.earthquakes.ui.di.ModelViewDataModule
import com.exercise.earthquakes.ui.interfaces.OnEarthquakeRetrieved

/**
 * Created by nativlevy on 2/15/18.
 */
class MainActivityViewModel(useNativeClient: Boolean, private val mEarthquakeReceived: OnEarthquakeRetrieved) {

    private var mRepository = Repository()

    init {
        prepare(useNativeClient)
    }

    fun prepare(useNativeClient: Boolean) {
        mRepository.reset()
        DaggerModelViewDataComponent.builder().modelViewDataModule(ModelViewDataModule(useNativeClient)).build().inject(mRepository)
    }

    fun requestEarthquakes() {
        mRepository.retrieveEarthquakes(mEarthquakeReceived)
    }
}