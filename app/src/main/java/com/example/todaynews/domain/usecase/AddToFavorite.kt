package com.example.todaynews.domain.usecase

import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.model.FavoriteArticle
import com.example.todaynews.domain.repository.favorite.FavoriteRepository

class AddToFavorite(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend fun invoke(articleNews: ArticleNews):Boolean {
        favoriteRepository.insertFavorite(FavoriteArticle(articleNews.id))
        return true
    }
}