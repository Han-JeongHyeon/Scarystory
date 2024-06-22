package com.horror.scarystory.componenet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.horror.scarystory.R
import com.horror.scarystory.Store.LocalRouterState
import com.horror.scarystory.Store.navigate

@Composable
fun HomeScreen() {
    val routeStore = LocalRouterState.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.requiredSize(
                com.horror.scarystory.activity.imageWidth,
                com.horror.scarystory.activity.imageHeight
            ),
            painter = painterResource(id = R.drawable.main_title),
            contentDescription = "메인 배너"
        )

        CustomDelay(delay = 1000) {
            routeStore.navigate("title")
        }
    }
}