package com.example.todaynews.domain.usecase

import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.repository.favorite.FavoriteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddOrRemoveFromFavoriteUsecase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val addToFavorite: AddToFavorite,
    private val removeFromFavorite: RemoveFromFavorite

) {
    suspend fun invoke(articleNews: ArticleNews){
        if(!articleNews.isAlreadyFavorite()){
            addToFavorite.invoke(articleNews)
            return
        }
        removeFromFavorite.invoke(articleNews)
    }

    private suspend fun ArticleNews.isAlreadyFavorite(): Boolean{
        val favoriteListIds = favoriteRepository.getFavoriteArticles().first().map {
            it.id
        }
        val isFavorite = favoriteListIds.contains(this.id)
        return isFavorite
    }
}