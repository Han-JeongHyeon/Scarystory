package com.horror.scarystory.componenet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.horror.scarystory.R
import com.horror.scarystory.enum.Route
import com.horror.scarystory.componenet.CustomDelay

@Composable
fun MainComponent(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "메인 화면",
            contentScale = ContentScale.Crop
        )

        Image(
            modifier = Modifier.requiredSize(imageWidth, imageHeight),
            painter = painterResource(id = R.drawable.main_title),
            contentDescription = "메인 배너"
        )

//        CustomDelay(navController = navController, route = Route.STORY_LIST.code)

    }

}

val imageWidth = 250.dp
val imageHeight = 220.dp