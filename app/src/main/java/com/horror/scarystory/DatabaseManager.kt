package com.horror.scarystory

import androidx.room.RoomDatabase
import com.horror.scarystory.DB.DAO.StoryDAO
import com.horror.scarystory.DB.DAO.UserDAO
import com.horror.scarystory.DB.DatabaseBuilder
import com.horror.scarystory.DB.StoryDatabase
import com.horror.scarystory.DB.UserDatabase
import com.horror.scarystory.enum.Database

object DatabaseManager {
    val userDatabase: UserDAO by lazy { databaseInitialize<UserDatabase>(Database.LOCAL_USER.code).useDB() }
    val storyDatabase: StoryDAO by lazy { databaseInitialize<StoryDatabase>(Database.LOCAL_STORY.code).useDB() }

    private inline fun <reified T : RoomDatabase> databaseInitialize(dbName: String): T {
        return DatabaseBuilder.getInstance(MyApplication.applicationContext(), T::class.java, dbName)
    }
}