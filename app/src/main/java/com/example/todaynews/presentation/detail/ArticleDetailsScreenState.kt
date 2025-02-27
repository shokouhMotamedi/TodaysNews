package com.example.todaynews.presentation.detail

import com.example.todaynews.presentation.home.SavableArticle

data class ArticleDetailsScreenState(
    val isLoading: Boolean = false,
    val article: SavableArticle? = null,
)
