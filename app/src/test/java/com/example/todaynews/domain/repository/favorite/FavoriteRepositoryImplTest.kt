package com.example.todaynews.domain.repository.favorite

import com.example.todaynews.data.local.FavoriteEntity
import com.example.todaynews.data.local.IFavoriteDao
import com.example.todaynews.domain.mapper.Mapper
import com.example.todaynews.domain.model.FavoriteArticle
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any


class FavoriteRepositoryImplTest{
    private lateinit var underTest: FavoriteRepositoryImpl

    @Mock
    lateinit var favoriteDao: IFavoriteDao

    @Mock
    lateinit var favoriteMapper: Mapper<FavoriteEntity, FavoriteArticle>

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        underTest = FavoriteRepositoryImpl(
            favoriteDao = favoriteDao,
            favoriteMapper = favoriteMapper
        )

    }

    @Test
    fun `underTest insertFavorite calls favoriteDao addToFavorite with FavoriteArticle`() = runBlocking{
        // Setup
        val mockedFavoriteArticle = getMockFavorite()
        val mockedEntityFavorite = getMockFavoriteEntity()
        `when`(favoriteDao.addToFavorite(any())).thenReturn(Unit)
        `when`(favoriteMapper.toData(any())).thenReturn(mockedEntityFavorite)

        // Act
        underTest.insertFavorite(mockedFavoriteArticle)

        // Verfication/Assertion
        verify(favoriteDao).addToFavorite(any())
    }


    /**
     * override suspend fun insertFavorite(article: FavoriteArticle) {
     *         favoriteDao.addToFavorite(
     *             favoriteMapper.toData(article)
     *
     */



    private fun getMockFavorite(): FavoriteArticle{
        return mock(FavoriteArticle::class.java)
    }

    private fun getMockFavoriteEntity(): FavoriteEntity{
        return mock(FavoriteEntity::class.java)
    }

}