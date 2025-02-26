package com.example.todaynews.domain.repository.article

import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.data.local.IArticleDao
import com.example.todaynews.domain.mapper.Mapper
import com.example.todaynews.domain.model.ArticleNews
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsArticleRepositoryImpl(
    private val articlesDao: IArticleDao,
    private val articlesMapper: Mapper<ArticleEntity, ArticleNews>
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
}