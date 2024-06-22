package com.horror.scarystory.Store

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

data class StoryStore(
    val stores: MutableState<List<String>> = mutableStateOf(listOf())
)

val LocalStoryStore = compositionLocalOf { StoryStore() }