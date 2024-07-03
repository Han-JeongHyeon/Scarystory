package com.horror.scarystory.componenet.Topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.horror.scarystory.Store.navigate
import com.horror.scarystory.Util.colorWhite
import com.horror.scarystory.Util.defaultColor
import com.horror.scarystory.Util.topBarHeight
import com.horror.scarystory.enum.Route

@Composable
fun CommonTopBar(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .height(topBarHeight)
            .fillMaxWidth()
            .background(defaultColor),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            content
        }
    }
}