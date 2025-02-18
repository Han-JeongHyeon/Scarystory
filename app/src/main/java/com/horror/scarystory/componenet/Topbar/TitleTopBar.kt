package com.horror.scarystory.componenet.Topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horror.scarystory.R
import com.horror.scarystory.Store.LocalRouterState
import com.horror.scarystory.Store.navigate
import com.horror.scarystory.Util.colorWhite
import com.horror.scarystory.componenet.Screen.StoryScreen
import com.horror.scarystory.enum.Route

@Composable
fun TitleTopBar(
    onclick: () -> Unit
) {
    val routeStore = LocalRouterState.current
    CommonTopBar {
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            modifier = Modifier
                .width(40.dp)
                .fillMaxHeight()
                .clickable {
                    onclick()
                },
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
                        .clickable {
                                   // dialog
                        },
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
                    .clickable { routeStore.navigate(Route.SETTING.code) },
                imageVector = Icons.Default.Settings,
                contentDescription = "설정",
                tint = colorWhite
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }

    @Composable
    fun SideBar() {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Sidebar
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .fillMaxHeight()
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Navigation",
                    fontSize = 20.sp,
                )
            }

            // Main Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Main Content Area")
            }
        }
    }
}