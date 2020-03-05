package com.andrew.newsapp.domain.newsDatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andrew.newsapp.domain.AppContext
import com.andrew.newsapp.entities.DbNewsPiece

private const val DB_NAME = "news database"

val dbInstance by lazy {
    Room.databaseBuilder(AppContext.context, NewsDb::class.java, DB_NAME).build()
}

@Database(entities = [DbNewsPiece::class], version = 1, exportSchema = false)
@TypeConverters(value = [MultiMediaConverters::class,StringConverters::class])
abstract class NewsDb : RoomDatabase() {
    abstract val topStoriesDao: TopStoriesDao
}