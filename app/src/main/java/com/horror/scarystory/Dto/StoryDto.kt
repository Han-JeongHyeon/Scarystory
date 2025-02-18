package com.horror.scarystory.Dto

import com.horror.scarystory.DB.Entity.Story
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class StoryDto(
    @SerialName("_id")
    var ID: ObjectId = ObjectId.get(), // 기본 키 필드,
    @SerialName("story_id")
    var STORY_ID: String,
    @SerialName("name")
    var NAME: String,
    @SerialName("content")
    var CONTENT: String,
    @SerialName("type")
    var TYPE: String,
    @SerialName("category")
    var CATEGORY: String,
)
