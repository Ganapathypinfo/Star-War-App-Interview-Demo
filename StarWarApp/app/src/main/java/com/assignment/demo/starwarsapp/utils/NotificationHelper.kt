package com.assignment.demo.starwarsapp.utils

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.assignment.demo.starwarsapp.R
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class NotificationHelper @Inject constructor(){

    fun setSnackBar(root: View?, snackBarMessage: String?) {
        val defaultSnackBarErrorMessage = root!!.context.resources.getString(R.string.error_msg)
        try {
            if (root != null && root.context != null) {
                val snackbar: Snackbar = if (snackBarMessage != null && !snackBarMessage.equals("", ignoreCase = true)
                ) getSnackBar(root, snackBarMessage) else {
                    getSnackBar(root, defaultSnackBarErrorMessage)
                }
                snackbar.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getSnackBar(root: View, snackBarMessage: String): Snackbar {
        val snackbar = Snackbar.make(root, snackBarMessage, Snackbar.LENGTH_SHORT)
        val view = snackbar.view
        val resources = root.context.resources
        view.setBackgroundColor(resources.getColor(R.color.snack_bar_background_color))
        val txtv = view.findViewById<TextView>(R.id.snackbar_text)
        txtv.setTextColor(Color.WHITE)
        txtv.textSize = 16f
        txtv.gravity = Gravity.CENTER_HORIZONTAL
        return snackbar
    }

    fun setSnackBarWithDefaultMessage(root: View) {
        val defaultSnackBarErrorMessage = root.context.resources.getString(R.string.error_msg)
        setSnackBar(root, defaultSnackBarErrorMessage)
    }

    fun setSnackBarWithNoInternetMessage(root: View) {
        val defaultSnackBarErrorMessage =
            root.context.resources.getString(R.string.no_internet_error_msg)
        setSnackBar(root, defaultSnackBarErrorMessage)
    }

    fun showDebugToast(context: Context?, toastText: String?) {
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
    }
}