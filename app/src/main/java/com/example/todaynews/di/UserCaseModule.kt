package com.example.todaynews.di

import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.data.remote.Article
import com.example.todaynews.data.remote.ArticlesApiService
import com.example.todaynews.domain.mapper.Mapper
import com.example.todaynews.domain.repository.article.NewsArticleRepository
import com.example.todaynews.domain.repository.favorite.FavoriteRepository
import com.example.todaynews.domain.usecase.AddOrRemoveFromFavoriteUsecase
import com.example.todaynews.domain.usecase.AddToFavorite
import com.example.todaynews.domain.usecase.FavoriteUseCase
import com.example.todaynews.domain.usecase.GetArticleByIdUsercase
import com.example.todaynews.domain.usecase.GetArticlesUseCase
import com.example.todaynews.domain.usecase.GetSavableArticlesUseCase
import com.example.todaynews.domain.usecase.RemoveFromFavorite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserCaseModule {

    @Provides
    @Singleton
    fun provideAddToFavoriteUsecase(favoriteRepository: FavoriteRepository): AddToFavorite {
        return AddToFavorite(favoriteRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveFromFavoriteUsecase(favoriteRepository: FavoriteRepository): RemoveFromFavorite {
        return RemoveFromFavorite(favoriteRepository)
    }

    @Provides
    @Singleton
    fun provideAddOrRemoveFromFavoriteUsecase(
        favoriteRepository: FavoriteRepository,
        addToFavorite: AddToFavorite,
        removeFromFavorite: RemoveFromFavorite
    ): AddOrRemoveFromFavoriteUsecase {
        return AddOrRemoveFromFavoriteUsecase(favoriteRepository, addToFavorite, removeFromFavorite)
    }

    @Provides
    @Singleton
    fun provideGetArticlesUsecase(
        articleRepository: NewsArticleRepository,
        apiService: ArticlesApiService,
        articleListMapper: Mapper<Article, ArticleEntity>
    ): GetArticlesUseCase {
        return GetArticlesUseCase(articleRepository, apiService, articleListMapper)
    }

    @Provides
    @Singleton
    fun provideFavoriteUsecase(
        favoriteRepository: FavoriteRepository,
        articleRepository: NewsArticleRepository
    ): FavoriteUseCase {
        return FavoriteUseCase(favoriteRepository, articleRepository)
    }

    @Provides
    @Singleton
    fun provideGetSavableArticlesUsecase(
        favoriteRepository: FavoriteRepository,
        getArticlesUseCase: GetArticlesUseCase
    ): GetSavableArticlesUseCase {
        return GetSavableArticlesUseCase(favoriteRepository, getArticlesUseCase)
    }

    @Provides
    @Singleton
    fun provideGetArticleByIdUsercase(
        articleRepository: NewsArticleRepository,
        favoriteRepository: FavoriteRepository
    ): GetArticleByIdUsercase {
        return GetArticleByIdUsercase(articleRepository, favoriteRepository)
    }
}