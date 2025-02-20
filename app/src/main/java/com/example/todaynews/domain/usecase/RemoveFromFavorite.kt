package com.example.todaynews.domain.usecase

import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.model.FavoriteArticle
import com.example.todaynews.domain.repository.favorite.FavoriteRepository

class RemoveFromFavorite(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun invoke(articleNews: ArticleNews){
        favoriteRepository.removeFromFavoriteArticle(FavoriteArticle(articleNews.id))
    }
}