package com.horror.scarystory

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

class Toast {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        fun initialize(baseContext: Context) {
            context = baseContext
        }
    }

    fun show(message: String, time: Int) {
        Toast.makeText(context, message, time).show()
    }

}