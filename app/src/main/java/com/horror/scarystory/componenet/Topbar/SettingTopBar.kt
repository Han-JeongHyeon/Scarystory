package com.horror.scarystory.componenet.Topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.horror.scarystory.Store.LocalRouterState
import com.horror.scarystory.Store.navigate
import com.horror.scarystory.Util.colorWhite
import com.horror.scarystory.enum.Route

@Composable
fun SettingTopBar() {
    val routeStore = LocalRouterState.current

    CommonTopBar {
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            modifier = Modifier
                .width(40.dp)
                .fillMaxHeight()
                .clickable { routeStore.navigate(Route.TITLE.code) },
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "뒤로가기",
            tint = colorWhite
        )
    }
}