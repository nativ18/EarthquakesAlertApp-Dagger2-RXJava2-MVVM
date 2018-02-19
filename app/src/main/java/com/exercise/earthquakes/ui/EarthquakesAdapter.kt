package com.exercise.earthquakes.ui

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exercise.earthquakes.R
import com.exercise.earthquakes.databinding.EarthquakeItemBinding
import com.exercise.earthquakes.model.EarthquakeItem
import com.exercise.earthquakes.ui.databinding.EarthquakeItemActions

/**
 * Created by nativlevy on 2/11/18.
 */
class EarthquakesAdapter(var items: ArrayList<EarthquakeItem> = ArrayList()) : RecyclerView.Adapter<EarthquakesAdapter.BaseViewHolder>() {

    companion object {
        val EARTHQUAKE_ITEM = 1
        val LOADING_ITEM = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return if (viewType == EARTHQUAKE_ITEM) {
            val viewDataBinding: EarthquakeItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.earthquake_item, parent, false)
            EarthquakeViewHolder(viewDataBinding)
        } else {
            LoadingViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.loading_item, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.count() > 0) {
            EARTHQUAKE_ITEM
        } else {
            LOADING_ITEM
        }
    }

    override fun getItemCount(): Int {
        return Math.max(1, items.size)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val itemType = getItemViewType(position)
        if (itemType == EARTHQUAKE_ITEM) {
            (holder as? EarthquakeViewHolder)?.bind(items[position])
        }
    }

    open class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    class LoadingViewHolder(root: View) : BaseViewHolder(root)

    class EarthquakeViewHolder(private val binder: EarthquakeItemBinding) : BaseViewHolder(binder.root) {

        fun bind(earthquakeItem: EarthquakeItem) {
            binder.eq = earthquakeItem
            binder.actionItem = EarthquakeItemActions()
            binder.executePendingBindings()
        }
    }
}