package com.horror.scarystory.componenet.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.horror.scarystory.R
import com.horror.scarystory.Store.LocalRouterState
import com.horror.scarystory.Store.navigate
import com.horror.scarystory.Util.imageDefaultHeight
import com.horror.scarystory.Util.imageDefaultWidth
import com.horror.scarystory.componenet.CustomDelay
import com.horror.scarystory.enum.Route

@Composable
fun HomeScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("loading.json"))

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.requiredSize(
                imageDefaultWidth,
                imageDefaultHeight
            ),
            painter = painterResource(id = R.drawable.main_title),
            contentDescription = "메인 배너"
        )

        LottieAnimation(composition, iterations = LottieConstants.IterateForever)
    }
}