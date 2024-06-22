package com.horror.scarystory.Store

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableIntStateOf
import com.horror.scarystory.R

data class SettingStore(
    val fontFamily: MutableState<Int> = mutableIntStateOf(R.font.yoondokrip),
    val fontSize: MutableState<Int> = mutableIntStateOf(22)
)

val LocalSettingStore = compositionLocalOf { SettingStore() }