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

class NotificationHelper @Inject constructor() {

    fun setSnackBar(root: View?, snackBarMessage: String?) {
        val defaultSnackBarErrorMessage = root!!.context.resources.getString(R.string.error_msg)
        try {
            if (root.context != null) {
                val snackBar: Snackbar =
                    if (snackBarMessage != null && !snackBarMessage.equals("", ignoreCase = true)
                    ) getSnackBar(root, snackBarMessage) else {
                        getSnackBar(root, defaultSnackBarErrorMessage)
                    }
                snackBar.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getSnackBar(root: View, snackBarMessage: String): Snackbar {
        val snackBar = Snackbar.make(root, snackBarMessage, Snackbar.LENGTH_SHORT)
        val view = snackBar.view
        val resources = root.context.resources
        view.setBackgroundColor(resources.getColor(R.color.snack_bar_background_color))
        val textSnackBar= view.findViewById<TextView>(R.id.snackbar_text)
        textSnackBar.setTextColor(Color.WHITE)
        textSnackBar.textSize = 16f
        textSnackBar.gravity = Gravity.CENTER_HORIZONTAL
        return snackBar
    }
}