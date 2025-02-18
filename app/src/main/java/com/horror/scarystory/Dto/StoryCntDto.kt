package com.horror.scarystory.Dto

import kotlinx.serialization.SerialName
import org.bson.types.ObjectId

data class StoryCntDto(
    @SerialName("_id")
    var ID: ObjectId = ObjectId.get(), // 기본 키 필드,
    @SerialName("story_id")
    var STORY_ID: String,
    @SerialName("view_cnt")
    var VIEW_CNT: Int = 0,
    @SerialName("rec_cnt")
    var REC_CNT: Int = 0,
)
