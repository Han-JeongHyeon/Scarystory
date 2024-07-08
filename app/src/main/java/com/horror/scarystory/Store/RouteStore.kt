package com.horror.scarystory.Store

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.horror.scarystory.Toast
import com.horror.scarystory.Toast.Companion.showToast
import com.horror.scarystory.enum.Route

var backPressTime = 0L

data class RouteStore(
    val currentRoute: MutableState<String> = mutableStateOf(Route.HOME.code),
    val queueRoute: MutableList<String> = mutableStateListOf()
)

val LocalRouterState = compositionLocalOf { RouteStore() }

fun RouteStore.navigate(route: String) {
    if (currentRoute.value == route) return
    currentRoute.value = route
    queueRoute.add(route)
}

fun RouteStore.back(finish: () -> Unit) {
    val currentTime = System.currentTimeMillis()
    if (queueRoute.isNotEmpty()) queueRoute.removeLast()
    currentRoute.value = queueRoute.lastOrNull() ?:
        return if (currentTime - backPressTime < 2500) {
            finish()
        } else {
            backPressTime = System.currentTimeMillis()
            showToast("'뒤로가기'를 버튼을 한번 더 눌러주세요", Toast.LENGTH_SHORT)
        }
}
