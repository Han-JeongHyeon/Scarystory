package com.horror.scarystory

import android.app.Application
import android.util.Log
import com.airbnb.lottie.utils.Logger


object Logger {

    fun debug(name: String, msg: String) {
        Log.d("[DEBUG] $name", msg)
    }

    fun warring(name: String, msg: String) {
        Log.w("[ERROR] $name", msg)
    }

    fun error(name: String, msg: String) {
        Log.e("[ERROR] $name", msg)
    }

    fun info(name: String, msg: String) {
        Log.i("[INFO] $name", msg)
    }

}