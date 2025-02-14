package com.example.todaynews.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites_list")
    fun getFavorites():Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun removeFromFavorite(favoriteEntity: FavoriteEntity)
}