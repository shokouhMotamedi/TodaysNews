package com.example.todaynews.domain.mapper

import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.domain.model.ArticleNews

class ArticleNewsMapper: Mapper<ArticleEntity, ArticleNews> {
    override fun toDomain(model: ArticleEntity): ArticleNews {
        return ArticleNews(
            id = model.id ?: -1,
            title = model.titleEntity,
            description = model.descEntity,
            imageResId = model.imageResIdEntity,
        )
    }

    override fun toData(model: ArticleNews): ArticleEntity {
        return ArticleEntity(
            id = model.id,
            titleEntity = model.title,
            descEntity = model.description,
            imageResIdEntity = model.imageResId,
        )
    }
}