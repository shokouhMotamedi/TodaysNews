package com.example.todaynews.domain.usecase

import com.example.todaynews.domain.repository.favorite.FavoriteRepository
import com.example.todaynews.presentation.home.SavableArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetSavableArticlesUseCase(
    private val favoriteRepository: FavoriteRepository,
    private val getArticlesUseCase: GetArticlesUseCase
) {

    fun invoke(): Flow<Result<List<SavableArticle>>>{
        return combine(
            favoriteRepository.getFavoriteArticles(),
            getArticlesUseCase.invoke()
        ){ favoriteArticles, usecaseResult ->
            val favoritesArticleIds = favoriteArticles.map { it.id }
            val articles = usecaseResult.articles
            val savableArticles = articles.map {
                SavableArticle(
                    article = it,
                    isFavorite = favoritesArticleIds.contains(it.id)
                )
            }
            when(usecaseResult){
                is UsecaseResult.Error -> Result.failure(Exception(usecaseResult.error))
                is UsecaseResult.Loading -> Result.success(emptyList())
                is UsecaseResult.Success -> Result.success(savableArticles)
            }
        }
    }
}