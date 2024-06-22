package com.horror.scarystory.componenet

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.darkColors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.horror.scarystory.R
import com.horror.scarystory.Story.story0

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StoryListComponent(navController: NavController) {

    val darkColorScheme = darkColorScheme()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "메인 화면",
            contentScale = ContentScale.Crop
        )

    }

//    MaterialTheme(
//        colorScheme = darkColorScheme(),
//        typography = MaterialTheme.typography,
//        shapes = MaterialTheme.shapes
//    ) {
//
//        Scaffold(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(darkColorScheme.background)
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(darkColorScheme.background)
//            ) {
//
//                ImageComponent(screenName = "main")
//
//            }
//        }
//    }

}