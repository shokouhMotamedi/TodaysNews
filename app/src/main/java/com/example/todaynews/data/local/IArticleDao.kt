package com.example.todaynews.data.local

import kotlinx.coroutines.flow.Flow

interface IArticleDao {

    fun getArticlesAsFlow(): Flow<List<ArticleEntity>>

    fun getArticlesWithIds(ids: List<Int>): Flow<List<ArticleEntity>>

    suspend fun insertArticle(articleEntity: ArticleEntity)

    suspend fun insertArticles(articles: List<ArticleEntity>)

    suspend fun removeArticle(articleEntity: ArticleEntity)
}