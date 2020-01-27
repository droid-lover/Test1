package com.vs.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vs.R
import com.vs.app.TestApp
import java.util.*

/**
 * Created by Sachin
 */
object Utils {

    private val TAG = Utils::class.java.name
    @Suppress("DEPRECATION")
    var sProgressDialog: ProgressDialog? = null
    private var sToast: Toast? = null
    var alertDialog: AlertDialog? = null

    fun showProgressDialog(context: Context) {
        showProcessingDialog(context, "Loading...")
    }


    private fun showProcessingDialog(context: Context, message: String) {
        if (context is AppCompatActivity || context is Activity) {

            if (alertDialog == null) {
                val builder = AlertDialog.Builder(context)
                val view = LayoutInflater.from(context)
                        .inflate(R.layout.layout_processing_dialog, null, false)
                (view.findViewById<View>(R.id.tv_processing_message) as TextView).text = message
                builder.setView(view)
                alertDialog = builder.create()
                alertDialog!!.setCancelable(false)
            }
            if (context is AppCompatActivity) {
                if (!(context as Activity).isFinishing)
                    alertDialog!!.show()
            }
        }
    }

    fun hideProgressDialog() {
        try {
            if (sProgressDialog != null && sProgressDialog!!.isShowing)
                sProgressDialog!!.dismiss()
            sProgressDialog = null

            if (alertDialog != null && alertDialog!!.isShowing)
                alertDialog!!.dismiss()
            alertDialog = null
        } catch (ignored: Throwable) {
        }

    }

    fun showToastMessage(strMessage: String?, length: Int = Toast.LENGTH_SHORT) {
        if (!TextUtils.isEmpty(strMessage)) {
            sToast?.cancel()
            sToast = Toast.makeText(TestApp.instance, strMessage, Toast.LENGTH_SHORT)
            sToast?.show()
        }
    }

    fun getTime(): String {
        return Calendar.getInstance().time.toString()
    }


}
