package com.horror.scarystory.Store

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.horror.scarystory.DB.Entity.Story
import com.horror.scarystory.Dto.StoryCntDto
import com.horror.scarystory.Dto.StoryDto

data class StoryStore(
    val stores: MutableState<List<String>> = mutableStateOf(listOf()),
    val sstores: SnapshotStateMap<String, Pair<StoryDto, Story>> = mutableStateMapOf(),
    var storyView: SnapshotStateMap<String, StoryCntDto> = mutableStateMapOf(),
    var currentStory: MutableState<String> = mutableStateOf("1"),
)

val LocalStoryStore = compositionLocalOf { StoryStore() }