package com.exercise.earthquakes.ui

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.exercise.earthquakes.R
import com.exercise.earthquakes.model.Earthquakes
import com.exercise.earthquakes.ui.interfaces.OnEarthquakeRetrieved
import com.exercise.earthquakes.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnEarthquakeRetrieved {

    private var mUseNativeViewModel = true
    private var mViewModel: MainActivityViewModel = MainActivityViewModel(mUseNativeViewModel, this)
    private var mAdapter: EarthquakesAdapter = EarthquakesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        setup()
        populate()
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.navigation_menu_native_vm -> {
                mUseNativeViewModel = true
            }
            R.id.navigation_menu_retrofit_vm -> {
                mUseNativeViewModel = false
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)

        mAdapter.items.clear()
        mAdapter.notifyDataSetChanged()
        populate()

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.main_action_map -> {
            val bundle = Bundle()
            bundle.putSerializable(MapActivity.EARTHQUAKE_LIST, mAdapter?.items)
            MapActivity.startActivity(this, bundle)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun populate() {
        mViewModel.prepare(mUseNativeViewModel)
        mViewModel.requestEarthquakes()
        showSnackbar()
    }

    private fun setup() {
        val recyclerView = findViewById(R.id.earthquake_recycler_view) as? RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = mAdapter
        recyclerView?.layoutManager = layoutManager
    }

    private fun showSnackbar() {
        val text = when {
            mUseNativeViewModel -> {
                getString(R.string.activity_main_urlconnection_vm_snackbar_text)
            }
            else -> {
                getString(R.string.activity_main_retrofit_vm_snackbar_text)
            }
        }
        val snackbar = Snackbar.make(findViewById(R.id.activity_navigation_content), text, Snackbar.LENGTH_LONG)
        val sbView = snackbar.view
        sbView.setBackgroundColor(Color.WHITE)
        val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        snackbar.show()
    }

    override fun onRetrieved(earthquakes: Earthquakes) {
        this@MainActivity.runOnUiThread({
            mAdapter.items = earthquakes.earthquakes
            mAdapter.notifyDataSetChanged()
        })
    }

    override fun onError(msg: String?) {
        this@MainActivity.runOnUiThread({
            val dialog = AlertDialog(this, View.OnClickListener {
                mViewModel.requestEarthquakes()
            }, msg, false)
            dialog.show()
        })
    }
}
