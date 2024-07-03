package com.horror.scarystory.componenet.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.AdSize
import com.horror.scarystory.Store.LocalRouterState
import com.horror.scarystory.Store.LocalStoryStore
import com.horror.scarystory.Store.navigate
import com.horror.scarystory.Util.topBarHeight
import com.horror.scarystory.componenet.Word

@Composable
fun TitleScreen() {
    val routeStore = LocalRouterState.current
    val storyStore = LocalStoryStore.current
    val storyList = storyStore.stores

    val maxHeight = LocalConfiguration.current.screenHeightDp
    val height = maxHeight.dp - AdSize.FULL_BANNER.height.dp - topBarHeight

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        itemsIndexed(storyList.value) { index, story ->
            Column(verticalArrangement = Arrangement.Top) {
                ListItem(
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    headlineContent = {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.clickable {
                                routeStore.navigate("story")
                            }
                        ) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Word(index = index, text = story)
                        }
                    },
                )
                Divider(color = Color.White, startIndent = 5.dp)
            }
        }
    }
}