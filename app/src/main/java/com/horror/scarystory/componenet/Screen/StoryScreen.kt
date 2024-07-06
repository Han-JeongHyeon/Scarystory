package com.horror.scarystory.componenet.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            Column(
                modifier = Modifier.weight(0.1f).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("대충 제목")
            }
            Column(
                modifier = Modifier.weight(0.9f),
            ) {
                Text("대충 내용")
            }

            // 공유, 다음 이전 글 버튼
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