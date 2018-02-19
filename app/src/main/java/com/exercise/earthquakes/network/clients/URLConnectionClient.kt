package com.exercise.earthquakes.network.clients

import android.os.AsyncTask
import com.exercise.earthquakes.model.Earthquakes
import com.exercise.earthquakes.network.EarthquakeRequestParams
import com.exercise.earthquakes.network.EarthquakeService.Companion.BASE_URL
import com.exercise.earthquakes.network.EarthquakeService.Companion.ENDPOINT
import com.exercise.earthquakes.network.NetworkConstants
import com.exercise.earthquakes.ui.interfaces.OnEarthquakeRetrieved
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by nativlevy on 2/11/18.
 */
class URLConnectionClient : BaseClient() {

    private fun executeRequest(eq: EarthquakeRequestParams): Pair<Int, String?> {
        val sb = StringBuilder(BASE_URL).append(ENDPOINT)
        sb.append("&north=").append(eq.north).append("&south=").append(eq.south).append("&east=").append(eq.east).append("&west=").append(eq.west).append("&username=").append(eq.username)

        val url = URL(sb.toString())
        val urlConnection = url.openConnection() as HttpURLConnection

        var isr: BufferedReader? = null
        try {

            val responseCode = urlConnection.responseCode
            isr = BufferedReader(InputStreamReader(urlConnection.inputStream))

            var inputLine: String? = ""
            val response = StringBuilder()

            while (inputLine != null) {
                response.append(inputLine)
                inputLine = isr.readLine()
            }
            return Pair(responseCode, response.toString())
        } catch (error: Exception) {
            return Pair(404, error.message)
        } finally {
            urlConnection.disconnect()
            isr?.close()
        }
    }

    override fun queryEarthquakes(params: EarthquakeRequestParams, onEarthquakeReceived: OnEarthquakeRetrieved) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute({
            val (responseCode, responseBody) = executeRequest(params)
            if (responseCode == NetworkConstants.REQUEST_OK) {
                onEarthquakeReceived.onRetrieved(Gson().fromJson(responseBody, Earthquakes::class.java))
            } else {
                onEarthquakeReceived.onError()
            }
        })
    }

    override fun release() {

    }

}
