package com.example.todaynews.data.local

import kotlinx.coroutines.flow.Flow

interface IFavoriteDao {

    fun getFavorites(): Flow<List<FavoriteEntity>>

    suspend fun addToFavoriteList(articles : List<FavoriteEntity>)

    suspend fun addToFavorite(articles: FavoriteEntity)

    suspend fun removeFromFavorite(favoriteEntity: FavoriteEntity)
}