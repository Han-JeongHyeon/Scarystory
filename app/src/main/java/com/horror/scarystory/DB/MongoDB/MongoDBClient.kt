package com.horror.scarystory.DB.MongoDB

import com.horror.scarystory.Dto.StoryCntDto
import com.horror.scarystory.Dto.StoryDto
import com.horror.scarystory.enum.Database
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo

class MongoDBClient private constructor() {

    companion object {
        val remoteStory by lazy{ getCollection<StoryDto>(Database.REMOTE_STORY.code) }
        val remoteViewer by lazy { getCollection<StoryCntDto>(Database.REMOTE_VIEWER.code) }

        private val client = KMongo.createClient()
        val database: MongoDatabase = client.getDatabase("ScaryStory")

        inline fun <reified T: Any> getCollection(collectionName: String): MongoCollection<T> {
            return database.getCollection(collectionName, T::class.java)
        }
    }
}