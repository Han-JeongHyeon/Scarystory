package com.horror.scarystory.DB

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object DatabaseBuilder {
    private val dbInstances: MutableMap<String, RoomDatabase> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <T : RoomDatabase> getInstance(context: Context, dbClass: Class<T>, dbName: String): T {
        return dbInstances[dbName] as? T ?: synchronized(dbInstances) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                dbClass,
                dbName
            ).build()
            dbInstances[dbName] = instance
            instance
        } as T
    }
}