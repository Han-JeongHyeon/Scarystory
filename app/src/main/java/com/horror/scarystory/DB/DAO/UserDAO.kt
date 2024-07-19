package com.horror.scarystory.DB.DAO

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.horror.scarystory.DB.Entity.User

@Dao
interface UserDAO {

    @Query("SELECT * FROM USER")
    fun getAll(): User?

    @Update
    fun updateUser(user: User)

}