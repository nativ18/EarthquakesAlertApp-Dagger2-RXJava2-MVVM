package com.exercise.earthquakes.ui.di

import com.exercise.earthquakes.network.EarthquakeRequestParams
import com.exercise.earthquakes.network.clients.BaseClient
import com.exercise.earthquakes.network.clients.URLConnectionClient
import com.exercise.earthquakes.network.clients.RetrofitClient
import dagger.Module
import dagger.Provides

/**
 * Created by nativlevy on 2/12/18.
 */
@Module
public class ModelViewDataModule(private val useNativeClient: Boolean) {

    @Provides
    fun provideViewModel(): BaseClient {
        return if (useNativeClient) {
            URLConnectionClient()
        } else {
            RetrofitClient()
        }
    }

    @Provides
    fun provideEarthquakeRequestParams(): EarthquakeRequestParams {
        return EarthquakeRequestParams(44.1f, -9.9f, -22.4f, 55.2f, "mkoppelman")
    }

}