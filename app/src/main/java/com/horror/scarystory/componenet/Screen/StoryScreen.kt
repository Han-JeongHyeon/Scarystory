package com.horror.scarystory.componenet.Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StoryScreen() {

    Box() {
        Column() {
            Text("대충 제목")
        }
        Column {
            Text("대충 내용")
        }
    }

}