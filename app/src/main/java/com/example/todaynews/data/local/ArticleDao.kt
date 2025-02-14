package com.example.todaynews.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao{

    @Query("SELECT * FROM article_tables")
    fun getArticlesAsFlow(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM article_tables WHERE id IN (:ids)")
    fun getArticlesWithIds(ids: List<Int>): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleEntity: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Delete
    suspend fun removeArticle(articleEntity: ArticleEntity)

}