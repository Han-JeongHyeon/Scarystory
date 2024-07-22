package com.horror.scarystory.componenet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.horror.scarystory.R
import com.horror.scarystory.activity.MainActivity

@Composable
fun ImageComponent(screenName: String) {

    when(screenName) {
        "main" -> {
            ImageComponent(drawable = R.drawable.main_background, contentScale = ContentScale.Crop)
        }
        "banner" -> {
            ImageComponent(drawable = R.drawable.main_background, width = imageWidth, height = imageHeight)
        }
    }

}

@Composable
fun ImageComponent(drawable: Int, contentScale: ContentScale = ContentScale.None) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = drawable),
        contentDescription = "",
        contentScale = contentScale
    )

}

@Composable
fun ImageComponent(drawable: Int, width: Dp, height: Dp, contentScale: ContentScale = ContentScale.None) {
    Image(
        modifier = Modifier.requiredSize(width, height),
        painter = painterResource(id = drawable),
        contentDescription = "",
    )
}