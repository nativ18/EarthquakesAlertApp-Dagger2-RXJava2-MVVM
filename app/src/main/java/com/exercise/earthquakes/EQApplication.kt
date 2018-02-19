package com.exercise.earthquakes

import android.app.Application

/**
 * Created by nativlevy on 2/11/18.
 */
class EQApplication : Application() {

    companion object {
        private lateinit var app: EQApplication

        fun getApplication(): EQApplication {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()

        app = this
    }
}