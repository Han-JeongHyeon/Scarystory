package com.horror.scarystory.componenet.Screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.ads.AdSize
import com.horror.scarystory.R
import com.horror.scarystory.Store.*
import com.horror.scarystory.Util.topBarHeight
import com.horror.scarystory.componenet.AD.BannerAd
import com.horror.scarystory.componenet.Topbar.CustomTopBar
import com.horror.scarystory.enum.Route
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val routeStore = LocalRouterState.current
    val route = routeStore.currentRoute
    val storyStore = LocalStoryStore.current
    val storyList = storyStore.stores

    val maxHeight = LocalConfiguration.current.screenHeightDp
    val height = maxHeight.dp - AdSize.FULL_BANNER.height.dp - topBarHeight

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    var drawerGesturesEnabled by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(scaffoldState.drawerState.isOpen) {
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = drawerGesturesEnabled,
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text("Home", modifier = Modifier.clickable {
                        // 홈 화면으로 이동
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Settings", modifier = Modifier.clickable {
                        // 설정 화면으로 이동
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Profile", modifier = Modifier.clickable {
                        // 프로필 화면으로 이동
                    })
                }
            }) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.main_background),
                    contentDescription = "메인 화면",
                    contentScale = ContentScale.Crop
                )

                Column {
                    CustomTopBar(
                        onclick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    )
                    AnimatedContent(
                        modifier = Modifier.clipToBounds(),
                        targetState = route.value,
                        transitionSpec = {
                            (slideInVertically(
                                initialOffsetY = { it }, // 아래에서 위로 슬라이드 인
                                animationSpec = tween(500)
                            ) + fadeIn(
                                animationSpec = tween(500)
                            ) with slideOutVertically(
                                targetOffsetY = { -it }, // 위로 슬라이드 아웃
                                animationSpec = tween(500)
                            ) + fadeOut(
                                animationSpec = tween(500)
                            )).using(
                                SizeTransform(clip = false)
                            )
                        }, label = ""
                    ) { targetState ->
                        when (targetState) {
                            Route.HOME.code -> HomeScreen()
                            Route.TITLE.code -> TitleScreen()
                            Route.SETTING.code -> SettingScreen()
                            Route.STORY.code -> StoryScreen()
                        }
                    }
                    if (route.value != Route.HOME.code) {
                        BannerAd()
                    }
                }
            }
    }
}