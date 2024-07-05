package com.horror.scarystory.Store

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.horror.scarystory.componenet.Screen.HomeScreen
import com.horror.scarystory.componenet.Screen.SettingScreen
import com.horror.scarystory.componenet.Screen.StoryScreen
import com.horror.scarystory.componenet.Screen.TitleScreen
import com.horror.scarystory.enum.Route

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

fun RouteStore.back(route: String, finish: () -> Unit) {
    if (currentRoute.value == route) return
    queueRoute.removeLast()
    currentRoute.value = queueRoute.lastOrNull() ?: return finish()
}
