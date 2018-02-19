package com.exercise.earthquakes.ui

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.exercise.earthquakes.R

/**
 * Created by nativlevy on 2/11/18.
 */
class AlertDialog(activity: Activity, onClickListener: View.OnClickListener?, content: String?, isCancelable: Boolean) : View.OnClickListener {

    private var dialog: Dialog? = null
    private var clickListener: View.OnClickListener? = null

    init {
        this.clickListener = onClickListener

        dialog = Dialog(activity)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.alert_dialog)

        val viewGroup = dialog?.findViewById<ViewGroup>(R.id.confirmation_dialog)
        viewGroup?.findViewById<View>(R.id.alert_dialog_btn)?.setOnClickListener(this)
        (viewGroup?.findViewById<View>(R.id.alert_dialog_content) as TextView).text = content

        dialog?.setCancelable(isCancelable)
    }

    override fun onClick(v: View?) {
        dismiss()
        clickListener?.onClick(v)
    }

    fun show() {
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}