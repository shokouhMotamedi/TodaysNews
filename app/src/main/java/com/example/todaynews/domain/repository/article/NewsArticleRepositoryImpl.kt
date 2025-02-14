package com.example.todaynews.domain.repository.article

import com.example.todaynews.data.local.ArticleDao
import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.data.local.FavoriteDao
import com.example.todaynews.data.local.FavoriteEntity
import com.example.todaynews.domain.mapper.ArticleNewsMapper
import com.example.todaynews.domain.model.ArticleNews
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsArticleRepositoryImpl(
    private val articlesDao: ArticleDao,
    private val favoriteDao: FavoriteDao,
    private val articlesMapper: ArticleNewsMapper
) : NewsArticleRepository {

    override fun getAllArticles(): Flow<List<ArticleNews>> {
        return articlesDao.getArticlesAsFlow().map { articleEntities ->
            articleEntities.map {
                articlesMapper.toDomain(it)
            }
        }
    }

    override suspend fun insertArticles(articles: List<ArticleEntity>) {
        articlesDao.insertArticles(articles)
    }

    override suspend fun removeArticles(articles: List<ArticleNews>) {
        articles.forEach { article ->
            val articleEntity = articlesMapper.toData(article)
            articlesDao.removeArticle(articleEntity)
        }
    }

    override fun getAllArticlesWithIds(ids: List<Int>): Flow<List<ArticleNews>> {
        return  articlesDao.getArticlesWithIds(ids).map { articleEntities -> articleEntities.map { it ->
            articlesMapper.toDomain(it)
        } }
    }

    override fun getFavorites(): Flow<List<Int>> {
       return favoriteDao.getFavorites().map { favoriteEntities -> favoriteEntities.map { it ->
           it.id
       } }
    }

    override suspend fun addToFavorite(articleNews: ArticleNews) {
       favoriteDao.addToFavorite(
           FavoriteEntity(
           id = articleNews.id
       )
       )
    }

    override suspend fun removeFromFavorite(articleNews: ArticleNews) {
        favoriteDao.removeFromFavorite(FavoriteEntity(
            id = articleNews.id
        ))
    }

    override fun getFavoriteArticles(): Flow<List<ArticleNews>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavoriteArticle(articleNews: ArticleNews) {
        TODO("Not yet implemented")
    }

}