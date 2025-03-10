package com.example.todaynews.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao: IFavoriteDao {

    @Query("SELECT * FROM favorites_list")
    override fun getFavorites():Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addToFavoriteList(articles : List<FavoriteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addToFavorite(articles: FavoriteEntity)


    @Delete
    override suspend fun removeFromFavorite(favoriteEntity: FavoriteEntity)
}