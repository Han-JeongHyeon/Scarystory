package com.horror.scarystory.activity

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.dp
import com.horror.scarystory.AdRequestService
import com.horror.scarystory.R
import com.horror.scarystory.Store.LocalRouterState
import com.horror.scarystory.Store.LocalStoryStore
import com.horror.scarystory.Store.back
import com.horror.scarystory.Store.navigate
import com.horror.scarystory.activity.ui.theme.ScarystoryTheme
import com.horror.scarystory.componenet.Screen.MainScreen
import com.horror.scarystory.enum.Route

class MainActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AdRequestService.initialize(this)

        setContent {
            val storyStore = LocalStoryStore.current
            val routeStore = LocalRouterState.current

            val route = routeStore.currentRoute
            val storyList = storyStore.stores
            storyList.value = resources.getStringArray(R.array.name).toList()

            ScarystoryTheme {
                MainScreen()

                BackHandler(enabled = true) {
                    routeStore.back(route.value) { finish() }
                }
            }
        }
    }

}
