package com.example.todaynews.domain.repository.article

import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.domain.model.ArticleNews
import kotlinx.coroutines.flow.Flow

interface NewsArticleRepository {

    fun getAllArticles(): Flow<List<ArticleNews>>

    suspend fun insertArticles(articles: List<ArticleEntity>)

    suspend fun removeArticles(articles: List<ArticleNews>)

    fun getAllArticlesWithIds(ids: List<Int>): Flow<List<ArticleNews>>

    fun getFavorites(): Flow<List<Int>>

    suspend fun addToFavorite(articleNews: ArticleNews)

    suspend fun removeFromFavorite(articleNews: ArticleNews)

    fun getFavoriteArticles(): Flow<List<ArticleNews>>

    suspend fun removeFromFavoriteArticle(articleNews: ArticleNews)

}