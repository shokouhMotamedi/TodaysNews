package com.example.todaynews.domain.usecase

import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.data.remote.Article
import com.example.todaynews.data.remote.ArticlesApiService
import com.example.todaynews.domain.mapper.ArticleListMapper
import com.example.todaynews.domain.mapper.Mapper
import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.repository.article.NewsArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * OFFLINE FIRST.
 * 1. Get the articles from the database and emit them with loading state
 * 2. Fetch the articles from the api
 * 3. Save the articles into local database/ update our cache
 * 4. Return the latest articles
 * SOLID
 * */
class GetArticlesUseCase @Inject constructor(
    private val articleRepository: NewsArticleRepository,
    private val apiService: ArticlesApiService,
    private val articlesResonseMapper: Mapper<Article, ArticleEntity>
) {

    fun invoke(): Flow<UsecaseResult>{
        return flow {
            // Step 1. Emit articles from the local database
            val articlesFromDatabase =
                articleRepository.getAllArticles().first()
            emit(UsecaseResult.Loading(articlesFromDatabase))
            // Step 2. Fetch articles from API
            val articlesResponse = apiService.fetchAllArticles()
            if (articlesResponse.isSuccessful){
                // Step 3. Save articles into Database
                val articles = articlesResponse.body()?.articles?.map {
                    articlesResonseMapper.toDomain(it)
                }
                articles?.let {
                   // articleRepository.removeArticles(it)
                    articleRepository.insertArticles(it)
                    val latestArticles = articleRepository.getAllArticles().first()
                    emit(UsecaseResult.Success(latestArticles))
                }
            } else {
                emit(UsecaseResult.Error(articlesFromDatabase,articlesResponse.message()))
            }
        }.catch {
            val articlesFromDatabase =
                articleRepository.getAllArticles().first()
            emit(UsecaseResult.Error(articlesFromDatabase,it.message))
        }
    }
}

sealed class UsecaseResult(
    open val articles: List<ArticleNews>
){
    class Loading(override val articles: List<ArticleNews>): UsecaseResult(articles)
    class Success(override val articles: List<ArticleNews>): UsecaseResult(articles)
    class Error(override val articles: List<ArticleNews>, val error: String?): UsecaseResult(articles)
}

sealed class GenericUsecaseResult<T>(
    open val data: T?
){ class Loading<T>(override val data: T): GenericUsecaseResult<T>(data)
    class Success<T>(override val data: T): GenericUsecaseResult<T>(data)
    class Error<T>(override val data: T?, val error: String?): GenericUsecaseResult<T>(data)
}
