package com.example.todaynews.presentation.home

import com.example.todaynews.domain.model.ArticleNews

interface FavoriteScreenAction {
    data class RemoveFromFavoritePage(val favoriteArticle: ArticleNews): FavoriteScreenAction
}