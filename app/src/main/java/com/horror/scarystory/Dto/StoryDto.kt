package com.horror.scarystory.Dto

import com.horror.scarystory.DB.Entity.Story

data class StoryDto(
    var ID: String,
    var NAME: String,
    var CONTENT: String,
    var READ_FG: Boolean,
    var FAVE_FG: Boolean,
    var REC_FG: Boolean,
    var TYPE: Boolean,
    var VIEW_CNT: Int = 0,
    var REC_CNT: Int = 0,
) {
    fun toEntity(): Story {
         return Story(
             ID = ID,
             NAME = NAME,
             CONTENT = CONTENT,
             READ_FG = READ_FG,
             FAVE_FG = FAVE_FG,
             REC_FG = REC_FG,
             TYPE = TYPE,
         )
    }
}
