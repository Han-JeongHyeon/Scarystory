package com.horror.scarystory.DB.DAO

import androidx.room.Query
import androidx.room.Update
import com.horror.scarystory.DB.Entity.User

interface UserDAO {

    @Query("SELECT * FROM USER")
    fun getAll(): User

    @Update
    fun updateUser(user: User)

}