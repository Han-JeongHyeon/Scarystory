package com.horror.scarystory.componenet.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.horror.scarystory.AdRequestService
import com.horror.scarystory.R
import com.horror.scarystory.activity.BaseActivity
import com.horror.scarystory.activity.MainActivity

@Composable
fun StoryScreen() {


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column() {
                Text("대충 제목")
            }
            Column {
                Text("대충 내용")
            }
        }

        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.left_btn),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.right_btn),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

    }

}