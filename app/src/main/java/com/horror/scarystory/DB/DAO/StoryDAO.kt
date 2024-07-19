package com.horror.scarystory.DB.DAO

import androidx.room.*
import com.horror.scarystory.DB.Entity.Story

@Dao
interface StoryDAO {
    @Insert
    fun insert(story: Story)

    @Update
    fun update(story: Story)

    @Query("SELECT * FROM STORY")
    fun getAll(): List<Story>

    @Upsert
    fun upsert(story: Story)
}