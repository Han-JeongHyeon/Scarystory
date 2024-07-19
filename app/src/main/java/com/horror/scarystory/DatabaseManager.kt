package com.horror.scarystory

import android.content.Context
import androidx.room.RoomDatabase
import com.horror.scarystory.DB.DatabaseBuilder
import com.horror.scarystory.DB.StoryDatabase
import com.horror.scarystory.DB.UserDatabase

object DatabaseManager {
    val userDatabase: UserDatabase by lazy { databaseInitialize(UserDatabase::class.java, "userDatabase") }
    val storyDatabase: StoryDatabase by lazy { databaseInitialize(StoryDatabase::class.java, "storyDatabase") }

    private fun <T : RoomDatabase> databaseInitialize(dbClass: Class<T>, dbName: String): T {
        return DatabaseBuilder.getInstance(MyApplication.applicationContext(), dbClass, dbName)
    }
}