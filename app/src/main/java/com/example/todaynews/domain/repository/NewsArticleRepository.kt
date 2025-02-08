package com.example.todaynews.domain.repository

import com.example.todaynews.domain.model.ArticleNews

interface NewsArticleRepository {
    suspend fun getAllArticles() : List<ArticleNews>
}