package com.horror.scarystory.componenet

import android.content.res.Resources
import android.util.Printer
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horror.scarystory.R
import com.horror.scarystory.Store.LocalRouterState
import com.horror.scarystory.Store.navigate
import com.horror.scarystory.Util.colorWhite
import com.horror.scarystory.Util.defaultColor
import com.horror.scarystory.Util.topBarHeight

@Composable
fun CustomTopBar() {
    val routeStore = LocalRouterState.current
    val route = routeStore.currentRoute

    when(route.value) {
        "home" -> {  }
        "title" -> {
            Box(
                modifier = Modifier
                    .height(topBarHeight)
                    .fillMaxWidth()
                    .background(defaultColor),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        modifier = Modifier
                            .width(40.dp)
                            .fillMaxHeight()
                            .clickable { },
                        imageVector = Icons.Default.Menu,
                        contentDescription = "메뉴",
                        tint = colorWhite
                    )
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Absolute.Right,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "10", fontSize = 17.sp, color = colorWhite)
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(
                                modifier = Modifier
                                    .size(25.dp, 25.dp)
                                    .clickable { },
                                imageVector = ImageVector.vectorResource(id = R.drawable.circle_outline),
                                contentDescription = "해석권",
                                tint = colorWhite
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Icon(
                            modifier = Modifier
                                .width(40.dp)
                                .fillMaxHeight()
                                .clickable { routeStore.navigate("setting") },
                            imageVector = Icons.Default.Settings,
                            contentDescription = "설정",
                            tint = colorWhite
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
        "setting" -> {
            Box(
                modifier = Modifier
                    .height(topBarHeight)
                    .fillMaxWidth()
                    .background(defaultColor),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        modifier = Modifier
                            .width(40.dp)
                            .fillMaxHeight()
                            .clickable { routeStore.navigate("title") },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로가기",
                        tint = colorWhite
                    )
                }
            }
        }
    }
}