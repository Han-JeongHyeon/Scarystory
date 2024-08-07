package com.horror.scarystory.componenet.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horror.scarystory.DatabaseManager
import com.horror.scarystory.R
import com.horror.scarystory.Store.LocalSettingStore
import com.horror.scarystory.Store.LocalStoryStore
import com.horror.scarystory.Store.toFontFamily
import com.horror.scarystory.Util.colorWhite
import com.horror.scarystory.activity.MainActivity
import com.horror.scarystory.componenet.Text
import kotlinx.coroutines.launch

@Composable
fun StoryScreen() {
    val scope = rememberCoroutineScope()

    val settingStore = LocalSettingStore.current
    val storyStore = LocalStoryStore.current

    val currentStory = storyStore.currentStory
    val font by settingStore.font
    val fontSize by settingStore.fontSize

    fun storyReadUpdate() {
        scope.launch {
            val story = storyStore.sstores[currentStory.value]
            if (story!!.second.READ_FG) {
                val data = story.second.copy(READ_FG = true)
                DatabaseManager.storyDatabase.upsert(data)
                story.copy(second = data)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(0.1f).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("대충 제목")
            }
            Column(
                modifier = Modifier.weight(0.9f),
            ) {
                Text("대충 내용", font = font, fontSize = fontSize)
            }

            // 공유, 다음 이전 글 버튼
        }

        Image(
            modifier = Modifier
                .size(30.dp)
                .clickable { println("이전글로 이동") },
            painter = painterResource(id = R.drawable.left_btn),
            contentDescription = "",
        )

        Image(
            modifier = Modifier
                .size(30.dp)
                .clickable { println("다음글로 이동") },
            painter = painterResource(id = R.drawable.right_btn),
            contentDescription = "",
        )



    }

}