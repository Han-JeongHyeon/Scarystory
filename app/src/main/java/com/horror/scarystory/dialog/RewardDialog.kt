package com.horror.scarystory.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog

@Composable
fun RewardDialog(
    title: String,
    isOpen: Boolean,
    width: Dp,
    height: Dp,
    onDismissRequest: () -> Unit,
) {

    if (!isOpen) return

    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Box(
            Modifier
                .size(width, height)
        ) {
            Column {
                Text(modifier = Modifier.fillMaxWidth().weight(0.7f), text = "~~~~")
                Row {
                    Button(onClick = {}) {
                        Text("")
                    }
                    Button(onClick = {}) {
                        Text("")
                    }
                }
            }
        }
    }

}