package com.example.todaynews.di

import android.content.Context
import androidx.room.Room
import com.example.todaynews.data.local.ArticleDao
import com.example.todaynews.data.local.ArticleDatabase
import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.data.local.FavoriteDao
import com.example.todaynews.data.local.FavoriteEntity
import com.example.todaynews.data.remote.Article
import com.example.todaynews.data.remote.ArticlesApiService
import com.example.todaynews.domain.mapper.ArticleListMapper
import com.example.todaynews.domain.mapper.ArticleNewsMapper
import com.example.todaynews.domain.mapper.FavoriteMapper
import com.example.todaynews.domain.mapper.Mapper
import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.model.FavoriteArticle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context.applicationContext, ArticleDatabase::class.java, "favorite_articles_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesArticleDao(
        articleDatabase: ArticleDatabase
    ): ArticleDao {
        return articleDatabase.dao()
    }

    @Provides
    @Singleton
    fun providesFavoriteDao(
        articleDatabase: ArticleDatabase
    ): FavoriteDao {
        return articleDatabase.favoriteDao()
    }

    @Provides
    @Singleton
    fun providesArticleNewsMapper(): Mapper<ArticleEntity, ArticleNews> {
        return ArticleNewsMapper()
    }

    @Provides
    @Singleton
    fun provideOkHttpClients(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                    ) // ✅ Pretend to be a real browser
                    .header("Accept", "application/json") // ✅ Force JSON response
                    .header("Connection", "close") // ✅ Prevent keep-alive issues
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://newsapi.org/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ArticlesApiService {
        return retrofit.create(ArticlesApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesArticleListMapper(): Mapper<Article, ArticleEntity> {
        return ArticleListMapper()
    }

    @Provides
    @Singleton
    fun providesFavoriteMapper(): Mapper<FavoriteEntity, FavoriteArticle> {
        return FavoriteMapper()
    }


}
