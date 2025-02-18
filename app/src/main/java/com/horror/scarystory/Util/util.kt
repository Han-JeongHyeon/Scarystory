package com.horror.scarystory.Util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.AdSize
import com.horror.scarystory.DB.MongoDB.MongoDBClient
import com.horror.scarystory.Dto.StoryDto
import com.horror.scarystory.enum.Database
import com.mongodb.client.MongoCollection
import org.litote.kmongo.updateOneById

val defaultColor = Color(0xFF252525)
val colorWhite = Color.White
val settingColor = Color(0xFFACACAC)

val topBarHeight = 50.dp

val imageDefaultWidth = 250.dp
val imageDefaultHeight = 220.dp

val defaultScreenMaxHeight = LocalConfiguration.current.screenHeightDp
val defaultHeight = defaultScreenMaxHeight.dp - AdSize.FULL_BANNER.height.dp - topBarHeight