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
import com.horror.scarystory.Toast
import com.horror.scarystory.activity.ui.theme.ScarystoryTheme
import com.horror.scarystory.componenet.Screen.MainScreen
import com.horror.scarystory.enum.Route
import com.horror.scarystory.service.MusicApplication

class MainActivity: BaseActivity() {

    companion object {
        var musicApplication = MusicApplication()
        val adRequestService by lazy { AdRequestService.getInstance() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AdRequestService.initialize(this)
        adRequestService.startAdLoading()

        Toast.initialize(baseContext)

        setContent {
            val storyStore = LocalStoryStore.current
            val routeStore = LocalRouterState.current

            val storyList = storyStore.stores
            storyList.value = resources.getStringArray(R.array.name).toList()

            ScarystoryTheme {
                MainScreen()

                BackHandler(enabled = true) {
                    routeStore.back { finish() }
                }
            }
        }
    }

}
