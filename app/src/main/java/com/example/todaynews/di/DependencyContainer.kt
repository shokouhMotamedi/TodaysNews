package com.example.todaynews.di

import android.content.Context
import com.example.todaynews.data.local.ArticleDao
import com.example.todaynews.data.local.ArticleDatabase
import com.example.todaynews.data.local.FavoriteDao
import com.example.todaynews.data.remote.ArticlesApiService
import com.example.todaynews.domain.mapper.ArticleListMapper
import com.example.todaynews.domain.mapper.ArticleNewsMapper
import com.example.todaynews.domain.mapper.FavoriteMapper
import com.example.todaynews.domain.repository.article.NewsArticleRepository
import com.example.todaynews.domain.repository.article.NewsArticleRepositoryImpl
import com.example.todaynews.domain.repository.favorite.FavoriteRepository
import com.example.todaynews.domain.repository.favorite.FavoriteRepositoryImpl
import com.example.todaynews.domain.usecase.AddOrRemoveFromFavoriteUsecase
import com.example.todaynews.domain.usecase.AddToFavorite
import com.example.todaynews.domain.usecase.FavoriteUseCase
import com.example.todaynews.domain.usecase.GetArticlesUseCase
import com.example.todaynews.domain.usecase.GetSavableArticlesUseCase
import com.example.todaynews.domain.usecase.RemoveFromFavorite
import com.example.todaynews.presentation.favorite.FavoriteViewModelFactory
import com.example.todaynews.presentation.home.HomeViewModelFactory

object DependencyContainer {
    private lateinit var newsArticleRepository: NewsArticleRepository
    lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var database: ArticleDatabase
    private lateinit var apiService: ArticlesApiService
    private lateinit var articleDao: ArticleDao
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var articlesMapper: ArticleNewsMapper
    private lateinit var articleListMapper: ArticleListMapper
    private lateinit var getArticlesUseCase: GetArticlesUseCase
    internal lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    private lateinit var addOrRemoveFromFavoriteUsecase: AddOrRemoveFromFavoriteUsecase
    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var favoriteUseCase: FavoriteUseCase
    private lateinit var favoriteMapper: FavoriteMapper
    private lateinit var addToFavorite: AddToFavorite
    private lateinit var removeFromFavorite: RemoveFromFavorite
    private lateinit var getSavableArticlesUseCase: GetSavableArticlesUseCase



    fun init(context: Context) {
        database = ArticleDatabase.getDatabase(context)
        articleDao = database.dao()
        favoriteDao = database.favoriteDao()
        articlesMapper = ArticleNewsMapper()
        articleListMapper = ArticleListMapper()
        apiService = ArticlesApiService.getArticlesApiService()
        favoriteMapper = FavoriteMapper()
        favoriteRepository = FavoriteRepositoryImpl(favoriteDao,favoriteMapper)
        addToFavorite = AddToFavorite(favoriteRepository)
        removeFromFavorite = RemoveFromFavorite(favoriteRepository)
        newsArticleRepository = NewsArticleRepositoryImpl(articleDao, articlesMapper)
        addOrRemoveFromFavoriteUsecase = AddOrRemoveFromFavoriteUsecase(favoriteRepository, addToFavorite, removeFromFavorite)
        getArticlesUseCase =
            GetArticlesUseCase(newsArticleRepository, apiService, articleListMapper)
        favoriteUseCase = FavoriteUseCase(favoriteRepository, newsArticleRepository)
        getSavableArticlesUseCase = GetSavableArticlesUseCase(favoriteRepository, getArticlesUseCase)
        homeViewModelFactory = HomeViewModelFactory(getSavableArticlesUseCase, addOrRemoveFromFavoriteUsecase)
        favoriteViewModelFactory = FavoriteViewModelFactory(favoriteUseCase =  favoriteUseCase, removeFromFavorite =  removeFromFavorite)
    }
}