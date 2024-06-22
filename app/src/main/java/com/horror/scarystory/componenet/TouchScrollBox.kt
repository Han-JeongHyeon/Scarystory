package com.horror.scarystory.componenet

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

@Composable
fun TouchScrollBox(block: @Composable () -> Unit) {

    Box(Modifier.fillMaxSize()) {
        val stateVertical = rememberScrollState(0)

        Box(
            modifier = Modifier
                .verticalScroll(stateVertical)
        ) {
            block()
        }
    }
}
