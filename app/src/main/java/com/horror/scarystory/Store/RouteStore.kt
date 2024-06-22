package com.horror.scarystory.Store

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

data class RouteStore(
    val currentRoute: MutableState<String> = mutableStateOf("home"),
)

val LocalRouterState = compositionLocalOf { RouteStore() }

fun RouteStore.navigate(route: String) {
    if (currentRoute.value == route) return
    currentRoute.value = route
}
