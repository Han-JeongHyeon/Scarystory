package com.horror.scarystory.componenet.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.AdSize
import com.horror.scarystory.R
import com.horror.scarystory.Store.*
import com.horror.scarystory.Util.topBarHeight
import com.horror.scarystory.componenet.AD.BannerAd
import com.horror.scarystory.componenet.Topbar.CustomTopBar
import com.horror.scarystory.enum.Route

@Composable
fun MainScreen() {
    val routeStore = LocalRouterState.current
    val route = routeStore.currentRoute
    val storyStore = LocalStoryStore.current
    val storyList = storyStore.stores

    val maxHeight = LocalConfiguration.current.screenHeightDp
    val height = maxHeight.dp - AdSize.FULL_BANNER.height.dp - topBarHeight

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "메인 화면",
            contentScale = ContentScale.Crop
        )
        Column {
            CustomTopBar()
            route.showScreen()
            if (route.value != Route.HOME.code) {
                BannerAd()
            }
        }
    }
}