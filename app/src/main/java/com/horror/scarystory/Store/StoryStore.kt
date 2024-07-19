package com.horror.scarystory.Store

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.horror.scarystory.DB.Entity.Story
import com.horror.scarystory.Dto.StoryCnt

data class StoryStore(
    val stores: MutableState<List<String>> = mutableStateOf(listOf()),
    val sstores: SnapshotStateMap<String, Story> = mutableStateMapOf(),
    val storyView: SnapshotStateMap<String, StoryCnt> = mutableStateMapOf(),
)

val LocalStoryStore = compositionLocalOf { StoryStore() }