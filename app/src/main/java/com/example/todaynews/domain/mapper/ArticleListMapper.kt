package com.example.todaynews.domain.mapper

import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.data.remote.Article
import com.example.todaynews.data.remote.ArticlesList
import com.example.todaynews.domain.model.ArticleNews
import kotlin.random.Random

class ArticleListMapper: Mapper<Article, ArticleEntity> {
    override fun toDomain(model: Article): ArticleEntity {
        return ArticleEntity(
            id = null,
            titleEntity = model.title,
            descEntity = model.description,
            imageResIdEntity = model.urlToImage
        )
    }

    override fun toData(model: ArticleEntity): Article {
        TODO("Not yet implemented")
    }
}