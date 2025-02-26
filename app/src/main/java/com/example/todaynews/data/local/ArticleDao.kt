package com.example.todaynews.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao: IArticleDao{

    @Query("SELECT * FROM article_tables")
    override fun getArticlesAsFlow(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM article_tables WHERE id IN (:ids)")
    override fun getArticlesWithIds(ids: List<Int>): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertArticle(articleEntity: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertArticles(articles: List<ArticleEntity>)

    @Delete
    override suspend fun removeArticle(articleEntity: ArticleEntity)

}