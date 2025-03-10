package com.example.todaynews.domain.repository.favorite

import com.example.todaynews.data.local.FavoriteEntity
import com.example.todaynews.data.local.IFavoriteDao
import com.example.todaynews.domain.mapper.Mapper
import com.example.todaynews.domain.model.FavoriteArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: IFavoriteDao,
    private val favoriteMapper: Mapper<FavoriteEntity, FavoriteArticle>
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