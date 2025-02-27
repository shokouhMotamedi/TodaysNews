package com.example.todaynews.domain.usecase

import com.example.todaynews.domain.repository.article.NewsArticleRepository
import com.example.todaynews.domain.repository.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/*
* 1. Fetch the favorites from the database
* 2. Fetch the articles from the database with the ids we got from step 1
* */
class FavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val articleRepository: NewsArticleRepository,
) {
    fun invoke(): Flow<UsecaseResult>{
        return combine(
            favoriteRepository.getFavoriteArticles(),
            articleRepository.getAllArticles()
        ){ favoriteArticles, articlesNews ->
            val favoriteArticleIds = favoriteArticles.map { it.id }
            val favoriteArticleNews =
                articlesNews.filter { favoriteArticleIds.contains(it.id) }
            UsecaseResult.Success(favoriteArticleNews)
        }
    }
}