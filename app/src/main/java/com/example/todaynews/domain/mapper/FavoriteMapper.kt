package com.example.todaynews.domain.mapper

import com.example.todaynews.data.local.FavoriteEntity
import com.example.todaynews.domain.model.FavoriteArticle

class FavoriteMapper : Mapper<FavoriteEntity, FavoriteArticle> {
    override fun toDomain(model: FavoriteEntity): FavoriteArticle {
        return FavoriteArticle(
            id = model.id
        )
    }

    override fun toData(model: FavoriteArticle): FavoriteEntity {
        return FavoriteEntity(
            id = model.id
        )
    }
}