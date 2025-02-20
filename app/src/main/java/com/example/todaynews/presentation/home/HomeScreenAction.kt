package com.example.todaynews.presentation.home

import com.example.todaynews.domain.model.ArticleNews

sealed interface HomeScreenAction {
    data class OnNewsClicked(val article: ArticleNews) : HomeScreenAction

    data class AddToFavorites(val article: ArticleNews) : HomeScreenAction

}

