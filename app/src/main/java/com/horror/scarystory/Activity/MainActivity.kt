package com.horror.scarystory.activity

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import com.horror.scarystory.*
import com.horror.scarystory.DB.Entity.User
import com.horror.scarystory.Store.*
import com.horror.scarystory.activity.ui.theme.ScarystoryTheme
import com.horror.scarystory.componenet.Screen.MainScreen
import com.horror.scarystory.service.MusicApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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
            val settingStore = LocalSettingStore.current

            LaunchedEffect(Unit) {
                this.launch(Dispatchers.IO) {
                    initializeStore(settingStore, storyStore)

                    val storyList = storyStore.stores
                    storyList.value = resources.getStringArray(R.array.name).toList()
                }
            }

            ScarystoryTheme {
                MainScreen()

                BackHandler(enabled = true) {
                    routeStore.back { finish() }
                }
            }
        }
    }

}

suspend fun initializeStore(
    settingStore: SettingStore,
    storyStore: StoryStore,
) {
    val userDatabase = DatabaseManager.userDatabase.useDB()
    val getUserData = userDatabase.getAll()
    val storyDatabase = DatabaseManager.storyDatabase.useDB()
    val getStoryData = storyDatabase.getAll()

    if (getUserData == null) {
        val UUID = UUID.randomUUID()
        userDatabase.updateUser(
            User(
                USER_ID = UUID,
                NAME = UUID.toString(),
                FONT_STYLE = settingStore.font.value,
                FONT_SIZE = settingStore.fontSize.value,
                MUSIC_FG = settingStore.isMusicUseYN.value,
                INTERPRET_TICKET = settingStore.interpretTicket.value,
            )
        )
    } else {
        settingStore.apply {
            font.value = getUserData.FONT_STYLE
            fontSize.value = getUserData.FONT_SIZE
            isMusicUseYN.value = getUserData.MUSIC_FG
            interpretTicket.value = getUserData.INTERPRET_TICKET
        }
    }

    getStoryData.forEach {
        it.toDto()
//        storyStore.sstores[it.ID].apply {
//            it.copy()
//        }
    }

}
