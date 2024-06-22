package com.horror.scarystory.activity

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.horror.scarystory.Type
import kotlinx.coroutines.delay

open class BaseActivity: ComponentActivity() {

    open fun ComponentActivity.toActivity(fromActivity: Class<*>, intentValue: String? = null) {
        val intent = Intent(this, fromActivity)
        if (intentValue != null) {
            intent.putExtra(Type.STORE_ID.code, intentValue)
        }
        startActivity(intent)
        finish()
    }

    @Composable
    open fun CustomDelay(delay: Long = 2000, action: @Composable () -> Unit) {
        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(visible) {
            delay(delay)
            visible = true
        }

        if (visible) {
            action()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()



    }

}