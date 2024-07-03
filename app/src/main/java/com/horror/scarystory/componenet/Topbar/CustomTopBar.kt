package com.horror.scarystory.componenet.Topbar

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
import com.google.android.gms.common.internal.service.Common
import com.horror.scarystory.R
import com.horror.scarystory.Store.LocalRouterState
import com.horror.scarystory.Store.navigate
import com.horror.scarystory.Util.colorWhite
import com.horror.scarystory.Util.defaultColor
import com.horror.scarystory.Util.topBarHeight
import com.horror.scarystory.componenet.Topbar.SettingTopBar
import com.horror.scarystory.componenet.Topbar.TitleTopBar
import com.horror.scarystory.enum.Route

@Composable
fun CustomTopBar() {
    val routeStore = LocalRouterState.current
    val route = routeStore.currentRoute

    when(route.value) {
        Route.TITLE.code -> {
            TitleTopBar()
        }
        Route.SETTING.code -> {
            SettingTopBar()
        }
        else -> {

        }
    }
}