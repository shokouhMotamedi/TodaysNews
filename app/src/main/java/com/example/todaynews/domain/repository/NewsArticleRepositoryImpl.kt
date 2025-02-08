package com.example.todaynews.domain.repository

import android.util.Log
import com.example.todaynews.data.remote.ArticlesApiService
import com.example.todaynews.data.remote.ArticlesList
import com.example.todaynews.domain.model.ArticleNews
import retrofit2.Response
import java.util.UUID

class NewsArticleRepositoryImpl(
    private val api: ArticlesApiService
) : NewsArticleRepository {
    override suspend fun getAllArticles(): List<ArticleNews> {
        return api.fetchAllArticles().toArticleNews()
    }

    private fun Response<ArticlesList>.toArticleNews(): List<ArticleNews> {
        return this.body()?.articles?.mapNotNull {
            ArticleNews(
                id = UUID.randomUUID().toString(),
                title = it.title,
                description = it.description,
                imageResId = it.urlToImage
            )
        } ?: emptyList()
    }
}