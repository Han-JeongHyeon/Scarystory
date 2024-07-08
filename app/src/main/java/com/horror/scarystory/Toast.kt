package com.horror.scarystory

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

class Toast {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        val LENGTH_SHORT = Toast.LENGTH_SHORT
        val LENGTH_LONG = Toast.LENGTH_LONG

        fun initialize(baseContext: Context) {
            context = baseContext
        }

        fun showToast(message: String, time: Int) {
            Toast.makeText(context, message, time).show()
        }
    }

}