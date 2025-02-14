package com.example.todaynews.presentation.home

import com.example.todaynews.domain.model.ArticleNews

data class StateViewModel(
    val isLoading : Boolean = false,
    val articles: List<SavableArticle> = emptyList()
)
