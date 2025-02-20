package com.example.todaynews.domain.repository.favorite

import com.example.todaynews.domain.model.FavoriteArticle
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteArticles(): Flow<List<FavoriteArticle>>

    suspend fun insertFavorite(article: FavoriteArticle)

    suspend fun removeFromFavoriteArticle(article: FavoriteArticle)
}