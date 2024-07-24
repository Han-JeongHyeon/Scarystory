package com.horror.scarystory.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.dp
import com.horror.scarystory.*
import com.horror.scarystory.DB.DatabaseBuilder
import com.horror.scarystory.DB.Entity.Story
import com.horror.scarystory.DB.Entity.User
import com.horror.scarystory.DB.MongoDB.MongoDBClient
import com.horror.scarystory.DB.UserDatabase
import com.horror.scarystory.Dto.StoryDto
import com.horror.scarystory.Store.*
import com.horror.scarystory.Story.story0
import com.horror.scarystory.Story.story1
import com.horror.scarystory.Story.story2
import com.horror.scarystory.Util.MongoDBUtil.findDataToMap
import com.horror.scarystory.activity.ui.theme.ScarystoryTheme
import com.horror.scarystory.componenet.Screen.MainScreen
import com.horror.scarystory.enum.Database
import com.horror.scarystory.enum.Route
import com.horror.scarystory.service.MusicApplication
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.litote.kmongo.updateOneById
import org.litote.kmongo.util.idValue
import java.util.*

class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val storyStore = LocalStoryStore.current
            val routeStore = LocalRouterState.current

            val storyList = storyStore.stores
            storyList.value = resources.getStringArray(R.array.name).toList()


//            val remoteStory = mongoDBClient.getCollection<StoryDto>(Database.REMOTE_STORY.code)
//            val remoteStoryDB = remoteStory.find().toList().associateBy { it.STORY_ID }.toMap()
//            val remoteViewer = mongoDBClient.getCollection<StoryDto>(Database.REMOTE_VIEWER.code)

//            resources.getStringArray(R.array.name).toList().forEachIndexed { index, s ->
//                val value: String = when (index / 100) {
//                    0 -> story0.getStory(index)
//                    1 -> story1.getStory(index % 100)
//                    2 -> story2.getStory(index % 200)
//                    else -> {
//                        "null"
//                    }
//                }
//                remoteStory.insertOne(StoryDto(
//                    STORY_ID = index.toString(),
//                    NAME = s,
//                    CONTENT = value,
//                    TYPE = "1",
//                    CATEGORY = "1",
//                ))
//            }

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
    activity: ComponentActivity,
) {
    AdRequestService.initialize(activity)
    MyApplication.adRequestService.startAdLoading()

    Toast.initialize(activity.baseContext)

    val remoteStory = MongoDBClient.remoteStory
    val remoteViewer = MongoDBClient.remoteViewer

    val storyDataMap = remoteStory.findDataToMap { it.STORY_ID }
    val viewerDataMap = remoteViewer.findDataToMap { it.STORY_ID }

    val userDatabase = DatabaseManager.userDatabase
    val getUserData = userDatabase.getAll()
    val storyDatabase = DatabaseManager.storyDatabase
    val getStoryData = storyDatabase.getAll().associateBy { it.ID }.toMap()

    // User data init
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

    storyDataMap.forEach {
        val story = getStoryData[it.key] ?: Story(
            ID = it.key,
        )
        storyStore.sstores[it.key] = it.value to story
    }

    storyStore.storyView = viewerDataMap

}
