package com.example.todaynews.presentation.home

import com.example.todaynews.domain.model.ArticleNews

data class SavableArticle(
    val article: ArticleNews,
    val isFavorite: Boolean = false
)
