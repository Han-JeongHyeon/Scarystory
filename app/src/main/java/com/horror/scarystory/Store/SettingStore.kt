package com.horror.scarystory.Store

import androidx.compose.runtime.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.horror.scarystory.R

data class SettingStore(
    var font: MutableState<Int> = mutableIntStateOf(R.font.yoondokrip),
    val fontSize: MutableState<Float> = mutableFloatStateOf(22F),
    val isMusicUseYN: MutableState<Boolean> = mutableStateOf(false)
)

val LocalSettingStore = compositionLocalOf { SettingStore() }

fun Int.toFontFamily() =
    FontFamily(Font(this, FontWeight.Bold, FontStyle.Normal))

val fontList = listOf(
    "독립체" to R.font.yoondokrip,
    "을지로체" to R.font.bneuljiro10,
    "비트로체" to R.font.bitro,
    "고딕체" to R.font.bm,
)