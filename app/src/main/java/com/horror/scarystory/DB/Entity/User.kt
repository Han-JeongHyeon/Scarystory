package com.horror.scarystory.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var NAME: String,
    var FONT_STYLE: Int,
    var FONT_SIZE: Int,
    var MUSIC_USE: Boolean,
    var INTERPRET_TICKET: Int,
) {
    @PrimaryKey(autoGenerate = true) var ID: Int = 0
}