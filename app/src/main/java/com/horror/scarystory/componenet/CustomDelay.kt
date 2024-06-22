package com.horror.scarystory.componenet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import kotlinx.coroutines.delay

//@Composable
//fun CustomDelay(delayTime: Long = 1500, route: String) {
//    var visible by remember { mutableStateOf(false) }
//
//    LaunchedEffect(visible) {
//        delay(delayTime)
//        visible = true
//    }
//}

@Composable
fun CustomDelay(delay: Long = 2000, action: @Composable () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        delay(delay)
        visible = true
    }

    if (visible) {
        action()
    }
}