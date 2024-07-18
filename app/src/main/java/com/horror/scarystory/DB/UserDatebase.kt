package com.horror.scarystory.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.horror.scarystory.DB.DAO.UserDAO
import com.horror.scarystory.DB.Entity.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDB(): UserDAO
}
