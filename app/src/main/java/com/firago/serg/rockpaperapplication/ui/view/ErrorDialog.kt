package com.firago.serg.rockpaperapplication.ui.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

class ErrorDialog: DialogFragment() {

    companion object {
        private const val  EXTRA_TITLE = "com.firago.serg.rockpaperapplication.ui.TITLE"
        private const val  EXTRA_MESSAGE = "com.firago.serg.rockpaperapplication.ui.MESSAGE"
        fun getInstance(title: String, message: String) : ErrorDialog {

            val errorDialog = ErrorDialog()
            val bundle = Bundle()
            bundle.putString(EXTRA_TITLE, title)
            bundle.putString(EXTRA_MESSAGE, message)
            errorDialog.arguments = bundle
            return errorDialog

        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(arguments!!.getString(EXTRA_TITLE))
        builder.setMessage(arguments!!.getString(EXTRA_MESSAGE))
        builder.setPositiveButton("Ok") { _, _->}

        return builder.create()
    }
}