package com.horror.scarystory.DB.MongoDB

import com.horror.scarystory.Dto.StoryDto
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

class MongoDBClient private constructor() {

    companion object {
        private val client = KMongo.createClient()
        val database: MongoDatabase = client.getDatabase("ScaryStory")
        private var instance: MongoDBClient = MongoDBClient()

        fun getInstance(): MongoDBClient {
            return instance
        }
    }

    inline fun <reified T: Any> getCollection(collectionName: String): MongoCollection<T> {
        return database.getCollection(collectionName, T::class.java)
    }
}