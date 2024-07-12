package com.horror.scarystory.componenet.Screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.AdSize
import com.horror.scarystory.Store.LocalSettingStore
import com.horror.scarystory.Store.fontList
import com.horror.scarystory.Util.colorWhite
import com.horror.scarystory.Util.settingColor
import com.horror.scarystory.Util.topBarHeight
import com.horror.scarystory.activity.MainActivity
import com.horror.scarystory.componenet.Text

@Composable
fun SettingScreen() {
    val maxHeight = LocalConfiguration.current.screenHeightDp
    val height = maxHeight.dp - AdSize.FULL_BANNER.height.dp - topBarHeight
    val settingStore = LocalSettingStore.current
    val fontFamily = settingStore.font
    val fontSize = settingStore.fontSize
    var isMusicUseYN by settingStore.isMusicUseYN

    val musicApplication = MainActivity.musicApplication

    fun music(checked: Boolean) {
        if (checked) musicApplication.start() else musicApplication.stop()
        isMusicUseYN = checked
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "폰트 설정", color = settingColor)
                Text(text = "폰트", fontSize = 20)
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth().height(30.dp)) {
                    fontList.map {
                        Column(
                            modifier = Modifier.weight(0.25f).fillMaxSize(),
                            Arrangement.Center,
                            Alignment.CenterHorizontally
                        ) {
                            Column(
                                Modifier
                                    .size(75.dp, 30.dp)
                                    .border(
                                        width = 2.dp,
                                        shape = RectangleShape,
                                        color = if (it.second == fontFamily.value) colorWhite else Color.Gray
                                    )
                                    .clickable {
                                        fontFamily.value = it.second
                                    },
                                Arrangement.Center,
                                Alignment.CenterHorizontally
                            ) {
                                Text(text = it.first)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "글자 크기", fontSize = 20)
                Spacer(modifier = Modifier.height(10.dp))
                Slider(
                    value = fontSize.value,
                    onValueChange = { fontSize.value = it },
                    valueRange = 0f..100f,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color.White, startIndent = 3.dp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "기타 설정", color = settingColor)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "배경음악 사용", fontSize = 20)
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Right,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(modifier = Modifier.padding(0.dp), checked = isMusicUseYN, onCheckedChange = { music(it) })
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color.White, startIndent = 3.dp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "문의하기", color = settingColor)
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clickable { }
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "업데이트 / 오류 문의", fontSize = 20)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}