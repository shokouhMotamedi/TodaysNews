package com.example.todaynews.presentation.home


data class StateViewModel(
    val isLoading : Boolean = false,
    val articles: List<SavableArticle> = emptyList()
)
