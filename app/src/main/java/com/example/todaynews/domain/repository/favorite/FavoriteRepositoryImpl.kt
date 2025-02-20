package com.example.todaynews.domain.repository.favorite

import com.example.todaynews.data.local.FavoriteDao
import com.example.todaynews.data.local.FavoriteEntity
import com.example.todaynews.domain.mapper.FavoriteMapper
import com.example.todaynews.domain.model.FavoriteArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val favoriteDao: FavoriteDao,
    private val favoriteMapper: FavoriteMapper
): FavoriteRepository {

    override fun getFavoriteArticles(): Flow<List<FavoriteArticle>> {
        return favoriteDao.getFavorites().map { favoriteEntities ->
            favoriteEntities.map {
                favoriteMapper.toDomain(it)
            }
        }
    }

    override suspend fun insertFavorite(article: FavoriteArticle) {
        favoriteDao.addToFavorite(
            favoriteMapper.toData(article)
        )
    }

    override suspend fun removeFromFavoriteArticle(article: FavoriteArticle) {
        favoriteDao.removeFromFavorite( FavoriteEntity(id = article.id ) )
    }

}