package com.example.todaynews.di

import com.example.todaynews.data.local.ArticleDao
import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.data.local.FavoriteDao
import com.example.todaynews.data.local.FavoriteEntity
import com.example.todaynews.domain.mapper.Mapper
import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.model.FavoriteArticle
import com.example.todaynews.domain.repository.article.NewsArticleRepository
import com.example.todaynews.domain.repository.article.NewsArticleRepositoryImpl
import com.example.todaynews.domain.repository.favorite.FavoriteRepository
import com.example.todaynews.domain.repository.favorite.FavoriteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoriteDao: FavoriteDao,
        favoriteMapper: Mapper<FavoriteEntity, FavoriteArticle>
    ): FavoriteRepository{
        return FavoriteRepositoryImpl(favoriteDao, favoriteMapper)
    }

    @Provides
    @Singleton
    fun provideNewsArticleRepository(
        articleDao: ArticleDao,
        articleMapper: Mapper<ArticleEntity, ArticleNews>
    ): NewsArticleRepository{
        return NewsArticleRepositoryImpl(articleDao, articleMapper)
    }
}