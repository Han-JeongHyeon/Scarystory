package com.horror.scarystory.componenet.Screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.ads.AdSize
import com.horror.scarystory.R
import com.horror.scarystory.Store.LocalSettingStore
import com.horror.scarystory.Store.toFontFamily
import com.horror.scarystory.Util.colorWhite
import com.horror.scarystory.Util.getAnimation
import com.horror.scarystory.Util.rightToLeftTransitionSpec
import com.horror.scarystory.activity.MainActivity
import com.horror.scarystory.Util.topBarHeight
import com.horror.scarystory.componenet.AD.BannerAd
import com.horror.scarystory.componenet.Text
import com.horror.scarystory.enum.Route

@Composable
fun StoryScreen() {
    val settingStore = LocalSettingStore.current
    val font by settingStore.font
    val fontSize by settingStore.fontSize

    val maxHeight = LocalConfiguration.current.screenHeightDp
    val height = maxHeight.dp - AdSize.FULL_BANNER.height.dp - topBarHeight

    val maxLength = 10
    val currentLength = remember { mutableIntStateOf(1) }
    val action = remember { mutableStateOf("") }
    val showMoveButton = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clickable {
                showMoveButton.value = !showMoveButton.value
            }
    ) {
        AnimatedContent(
            modifier = Modifier.clipToBounds(),
            targetState = currentLength.intValue,
            transitionSpec = getAnimation(action.value),
            label = ""
        ) { targetState ->

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                ) {
                    this.item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text("대충 제목" + targetState)
                        }

                        Column(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Text(
                                "수류탄파편을 눈에 맞은 남자가 앞이 안보이는 공포와 고통으로 비명을 지르며 기어다니고 있다.\n" +
                                        "\n" +
                                        "\n" +
                                        "그러던 중 남자는 좁은 깊은 구덩이 속으로 빠지게 된다.\n" +
                                        "\n" +
                                        "\n" +
                                        "그 속엔 꽤 많은 사람들이 있는것 같았고, 그들은 일제히 공포에 질려 비명을 지르거나 발버둥 치고 있었다.\n" +
                                        "\n" +
                                        "\n" +
                                        "하지만, 앞을 볼 수 없었던 그 남자는 상황을 알 수 없어\n" +
                                        "\n" +
                                        "\n" +
                                        "그저 좁고 깊은 그 곳에서 공포를 느끼며 살고 싶은 욕망만에 시달렸다.\n" +
                                        "\n" +
                                        "\n" +
                                        "우리가 그를 발견 한 건, 폐허가 된 마을에서 생존자를 찾으러 갔을 때 였다.\n" +
                                        "\n" +
                                        "\n" +
                                        "그는 텅빈 우물 속에서 기적적으로 구조되었다.\n" +
                                        "\n" +
                                        "\n" +
                                        "[출처] 네이버 블로그\n", font = font, fontSize = fontSize
                            )
                        }
                    }
                }
            }

            // 공유, 다음 이전 글 버튼
        }

        if (showMoveButton.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (currentLength.intValue != 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            modifier = Modifier
                                .size(25.dp, 70.dp)
                                .clickable {
                                    action.value = "left"
                                    currentLength.intValue -= 1
                                },
                            painter = painterResource(id = R.drawable.left_btn),
                            contentDescription = "",
                        )
                    }
                }

                if (currentLength.intValue != maxLength) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            modifier = Modifier
                                .size(25.dp, 70.dp)
                                .clickable {
                                    action.value = "right"
                                    currentLength.intValue += 1
                                },
                            painter = painterResource(id = R.drawable.right_btn),
                            contentDescription = "",
                        )
                    }
                }
            }
        }
    }
}