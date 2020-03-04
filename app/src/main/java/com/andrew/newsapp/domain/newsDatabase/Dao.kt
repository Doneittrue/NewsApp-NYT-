package com.andrew.newsapp.domain.newsDatabase

import androidx.room.*
import com.andrew.newsapp.entities.DbNewsPiece

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(vararg news: DbNewsPiece)

    @Query("DELETE FROM DbNewsPiece WHERE NOT isFavourite")
    fun deleteAll()

    @Query("SELECT * FROM DbNewsPiece")
    fun queryAll(): List<DbNewsPiece>

    @Query("SELECT * FROM DbNewsPiece WHERE isFavourite")
    fun queryFavourites(): List<DbNewsPiece>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateItem(story: DbNewsPiece)
}