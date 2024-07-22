package com.horror.scarystory.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.horror.scarystory.Dto.StoryDto

@Entity
data class Story (
    @PrimaryKey var ID: String,
    var READ_FG: Boolean = false,
    var FAVE_FG: Boolean = false,
    var REC_FG: Boolean = false,
) {
//    fun toDto(): StoryDto {
//        return StoryDto(
//            ID = ID,
//            NAME = NAME,
//            CONTENT = CONTENT,
//            READ_FG = READ_FG,
//            FAVE_FG = FAVE_FG,
//            REC_FG = REC_FG,
//            TYPE = TYPE,
//        )
//    }
}