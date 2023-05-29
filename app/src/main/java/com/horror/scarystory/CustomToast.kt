package com.horror.scarystory

import android.view.ContextThemeWrapper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CustomToast(
    thisActivity: AppCompatActivity,
    msg: String
) {

    init {
        Toast.makeText(thisActivity, msg, Toast.LENGTH_LONG).show()
    }
}