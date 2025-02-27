package com.example.todaynews.domain.usecase

import com.example.todaynews.domain.repository.article.NewsArticleRepository
import com.example.todaynews.domain.repository.favorite.FavoriteRepository
import com.example.todaynews.presentation.home.SavableArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class GetArticleByIdUsercase @Inject constructor(
    private val articleRepository: NewsArticleRepository,
    private val favoriteRepository: FavoriteRepository
) {
    fun invoke(articleId: Int): Flow<GenericUsecaseResult<SavableArticle>> {
        return combine(
            articleRepository.getAllArticlesWithIds(listOf(articleId)),
            favoriteRepository.getFavoriteArticles()
        ){ articles, favorites ->
            val favoriteArticleIds = favorites.map { it.id }
            val savableArticles = articles.map { artcielNews ->
                SavableArticle(
                    article = artcielNews,
                    isFavorite = favoriteArticleIds.contains(artcielNews.id)
                )
            }
            GenericUsecaseResult.Success(
                data = savableArticles.first()
            )
        }.catch {
            GenericUsecaseResult.Error(data = null,error = "ArticleNoFoundWithId")
        }

    }
}