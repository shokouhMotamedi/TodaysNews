package com.example.todaynews.di

import android.content.Context
import com.example.todaynews.data.local.ArticleDao
import com.example.todaynews.data.local.ArticleDatabase
import com.example.todaynews.data.local.FavoriteDao
import com.example.todaynews.data.remote.ArticlesApiService
import com.example.todaynews.domain.mapper.ArticleListMapper
import com.example.todaynews.domain.mapper.ArticleNewsMapper
import com.example.todaynews.domain.repository.article.NewsArticleRepository
import com.example.todaynews.domain.repository.article.NewsArticleRepositoryImpl
import com.example.todaynews.domain.usecase.GetArticlesUseCase
import com.example.todaynews.presentation.favorite.FavoriteViewModelFactory
import com.example.todaynews.presentation.home.HomeViewModelFactory

object DependencyContainer {
    private lateinit var newsArticleRepository: NewsArticleRepository
    lateinit var homeViewModelFactory: HomeViewModelFactory
    private lateinit var database: ArticleDatabase
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    private lateinit var apiService: ArticlesApiService
    private lateinit var articleDao: ArticleDao
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var articlesMapper: ArticleNewsMapper
    private lateinit var articleListMapper: ArticleListMapper
    private lateinit var getArticlesUseCase: GetArticlesUseCase

    fun init(context: Context) {
        database = ArticleDatabase.getDatabase(context)
        articleDao = database.dao()
        favoriteDao = database.favoriteDao()
        articlesMapper = ArticleNewsMapper()
        articleListMapper = ArticleListMapper()
        apiService = ArticlesApiService.getArticlesApiService()
        newsArticleRepository = NewsArticleRepositoryImpl(articleDao, favoriteDao, articlesMapper)
        getArticlesUseCase =
            GetArticlesUseCase(newsArticleRepository, apiService, articleListMapper)
        homeViewModelFactory = HomeViewModelFactory(newsArticleRepository, getArticlesUseCase)

    }
}