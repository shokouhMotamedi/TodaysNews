package com.example.todaynews.domain.mapper

import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.domain.model.ArticleNews
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ArticleNewsMapperTest {

    private lateinit var underTest: ArticleNewsMapper

    @Test
    fun `toDomain map the ArticleEntity into ArticleNews correctly`() {
        underTest = ArticleNewsMapper()
        val article = ArticleEntity(
            id = 1,
            titleEntity = "Title",
            descEntity = "description",
            imageResIdEntity = "String"
        )

        val expectedResult = article.id?.let {
            ArticleNews(
                id = it,
                title = article.titleEntity,
                description = article.descEntity,
                imageResId = article.imageResIdEntity
            )
        }

        val actualResult = underTest.toDomain(article)
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `toData map the ArticleNew to ArticleEntity correctly`() {
        underTest = ArticleNewsMapper()
        val article = ArticleNews(
            id = 1,
            title = "title",
            description = "description",
            imageResId = "String"
        )

        val expectedResult = ArticleEntity(
            id = article.id,
            titleEntity = article.title,
            descEntity = article.description,
            imageResIdEntity = article.imageResId,
        )

        val actualResult = underTest.toData(article)
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `toData map the ArticleNew to ArticleEntity doesn't correctly`() {
        underTest = ArticleNewsMapper()
        val article = ArticleNews(
            id = 1,
            title = "title",
            description = "description",
            imageResId = "String"
        )

        val expectedResult = ArticleEntity(
            id = 2,
            titleEntity = article.title,
            descEntity = article.description,
            imageResIdEntity = article.imageResId,
        )

        val actualResult = underTest.toData(article)
        assertNotEquals(expectedResult, actualResult)
    }

}