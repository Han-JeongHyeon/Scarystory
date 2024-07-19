package com.horror.scarystory.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.horror.scarystory.DB.DAO.StoryDAO
import com.horror.scarystory.DB.DAO.UserDAO
import com.horror.scarystory.DB.Entity.Story
import com.horror.scarystory.DB.Entity.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun useDB(): UserDAO
}

@Database(entities = [Story::class], version = 1)
abstract class StoryDatabase: RoomDatabase() {
    abstract fun useDB(): StoryDAO
}
