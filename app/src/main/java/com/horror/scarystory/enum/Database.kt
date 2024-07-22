package com.horror.scarystory.enum

enum class Database(val code: String) {
    // Remote DB name (mongoDB)
    REMOTE_STORY("Story"),
    REMOTE_VIEWER("Viewer"),

    // Local DB name (Room)
    LOCAL_USER("UserDatabase"),
    LOCAL_STORY("StoryDatabase"),
}