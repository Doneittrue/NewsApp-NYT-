package com.andrew.newsapp.domain.newsDatabase

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.andrew.newsapp.entities.DbMultimedia
import com.andrew.newsapp.entities.DbNewsPiece

@Dao
interface TopStoriesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(news: List<DbNewsPiece>?)

    @Query("DELETE FROM DbNewsPiece WHERE NOT isFavourite")
    fun deleteAll()

    @Query("SELECT * FROM DbNewsPiece")
    fun queryAll(): DataSource.Factory<Int, DbNewsPiece>

    @Query("SELECT * FROM DbNewsPiece WHERE isFavourite")
    fun queryFavourites(): LiveData<List<DbNewsPiece>>

    @Query("DELETE FROM DbNewsPiece WHERE title LIKE :title")
    fun deleteItem(title: String)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateItem(story: DbNewsPiece)
}

@Dao
interface MultimediaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(multimedia: List<DbMultimedia>)

    @Query("DELETE FROM DbMultimedia WHERE newsTitle LIKE :title ")
    fun deleteMultimedia(title: String)

    @Query("SELECT * FROM DbMultimedia WHERE newsTitle LIKE :title ")
    fun retrieveMultimedia(title: String): LiveData<List<DbMultimedia>>
}