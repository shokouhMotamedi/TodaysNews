package com.example.todaynews.domain.mapper

import com.example.todaynews.data.local.FavoriteEntity
import com.example.todaynews.domain.model.FavoriteArticle
import org.junit.Assert.assertEquals
import org.junit.Test


class FavoriteMapperTest {

    private lateinit var underTest: FavoriteMapper

    @Test
    fun `toDomain map the FavoriteEntity to FavoriteArticle correctly`() {
        underTest = FavoriteMapper()
        val article = FavoriteEntity(id = 1)

        val expectedResult = FavoriteArticle(id = article.id)

        val actualResult = underTest.toDomain(article)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `toData map the FavoriteArticle to FavoriteEntity correctly`() {
        underTest = FavoriteMapper()
        val article = FavoriteArticle(id = 1)

        val expectedResult = FavoriteEntity(id = article.id)

        val actualResult = underTest.toData(article)

        assertEquals(expectedResult, actualResult)
    }
}