package com.horror.scarystory.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.dp
import com.horror.scarystory.AdRequestService
import com.horror.scarystory.DB.DatabaseBuilder
import com.horror.scarystory.DB.Entity.User
import com.horror.scarystory.DB.UserDatabase
import com.horror.scarystory.R
import com.horror.scarystory.Store.*
import com.horror.scarystory.Toast
import com.horror.scarystory.activity.ui.theme.ScarystoryTheme
import com.horror.scarystory.componenet.Screen.MainScreen
import com.horror.scarystory.enum.Route
import com.horror.scarystory.service.MusicApplication

class MainActivity: BaseActivity() {

    companion object {
        var musicApplication = MusicApplication()
        val adRequestService by lazy { AdRequestService.getInstance() }
        val userDatabase: UserDatabase by lazy { getInstance() }

        private fun databaseInitialize(context: Context) {
            MainActivity().baseContext
            if (userDatabase == null) {
                userDatabase = DatabaseBuilder.getInstance(context, UserDatabase::class.java, "userDatabase")
            }
        }

        private fun getInstance(): UserDatabase {
            return userDatabase ?: throw IllegalStateException("AdRequestService must be initialized")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseInitialize(this)

        val userDatabase = DatabaseBuilder.getInstance(baseContext, UserDatabase::class.java, "userDatabase")

        AdRequestService.initialize(this)
        adRequestService.startAdLoading()

        Toast.initialize(baseContext)

        setContent {
            val storyStore = LocalStoryStore.current
            val routeStore = LocalRouterState.current
            var settingStore = LocalSettingStore.current

            initializeStore(settingStore)

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

fun initializeStore(
    settingStore: SettingStore
) {
    val userDatabase = MainActivity.userDatabase.userDB()

    settingStore.font.value = userDatabase.getAll().FONT_STYLE
}
