package com.exercise.earthquakes.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.exercise.earthquakes.R
import com.exercise.earthquakes.extensions.launchActivity
import com.exercise.earthquakes.network.Utils
import com.skyfishjy.library.RippleBackground


/**
 * Created by nativlevy on 2/11/18.
 */
class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val DELAY_IN_MILLIS: Long = 2000
    }

    private lateinit var mRippleBackground: RippleBackground

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mRippleBackground = findViewById<View>(R.id.red_circle_ripple) as RippleBackground
    }

    private fun loadNextScreen() {
        mRippleBackground.startRippleAnimation()

        var dialog: AlertDialog? = null

        Handler().postDelayed({
            if (Utils.hasNetwork()) {
                dialog?.dismiss()
                mRippleBackground.stopRippleAnimation()
                launchActivity<MainActivity>()
                finish()
            } else {
                dialog = AlertDialog(this, View.OnClickListener { loadNextScreen() }, getString(R.string.no_network), false)
                dialog?.show()
            }
        }, DELAY_IN_MILLIS)
    }

    override fun onResume() {
        super.onResume()
        loadNextScreen()
    }
}
