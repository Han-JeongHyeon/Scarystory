package com.horror.scarystory.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class User(
    var USER_ID: UUID,
    var NAME: String,
    var FONT_STYLE: Int,
    var FONT_SIZE: Float,
    var MUSIC_FG: Boolean,
    var INTERPRET_TICKET: Int,
) {
    @PrimaryKey(autoGenerate = true) var ID: Int = 0
}