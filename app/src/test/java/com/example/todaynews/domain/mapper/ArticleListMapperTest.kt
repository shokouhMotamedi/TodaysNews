package com.example.todaynews.domain.mapper

import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.data.remote.Article
import com.example.todaynews.data.remote.Source
import org.junit.Test

class ArticleListMapperTest {
    private lateinit var underTest: ArticleListMapper

    @Test
    fun `toDomain maps the Article into ArticleEntity correctly`() {
        underTest = ArticleListMapper()
        val article = Article(
            id = 1, author = "Name", content = "News", description = "Description",
            publishedAt = "PublishedAt", source = Source("1", "Name"),
            title = "Title", url = "String", urlToImage = "String"
        )
        val expectedResult = ArticleEntity(
            id = null,
            titleEntity = article.title,
            descEntity = article.description,
            imageResIdEntity = article.urlToImage
        )
        val actualResult = underTest.toDomain(article)
        assert(expectedResult == actualResult)
    }
}