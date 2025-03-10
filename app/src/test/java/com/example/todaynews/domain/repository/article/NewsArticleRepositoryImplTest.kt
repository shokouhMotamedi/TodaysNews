package com.example.todaynews.domain.repository.article

import com.example.todaynews.data.local.ArticleEntity
import com.example.todaynews.data.local.IArticleDao
import com.example.todaynews.domain.mapper.Mapper
import com.example.todaynews.domain.model.ArticleNews
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times
import org.mockito.kotlin.any


class NewsArticleRepositoryImplTest{
    private lateinit var underTest: NewsArticleRepository
    @Mock
    lateinit var articlesDao: IArticleDao

    @Mock
    lateinit var articlesMapper: Mapper<ArticleEntity, ArticleNews>

    @Before
    fun setup(){
       // articlesMapper = ArticleNewsMapper()
        MockitoAnnotations.openMocks(this)
        underTest = NewsArticleRepositoryImpl(
            articlesDao = articlesDao,
            articlesMapper = articlesMapper
        )
    }

    @Test
    fun `underTest insertArticles calls articlesDao insertArticles with articles`() = runBlocking {
        val mockedArticles = getMockArticleEntityList()
        underTest.insertArticles(mockedArticles)
        `when`(articlesDao.insertArticles(mockedArticles)).thenReturn(Unit)
        verify(articlesDao).insertArticles(mockedArticles)
    }

    @Test
    fun `underTest removeArticles with articles calls articlesDao removeArticles for every articles`() = runBlocking {
        // Setup
        val mockedArticles = getMockArticleNews()
        val mockedArticleEntity = mock(ArticleEntity::class.java)
        `when`(articlesDao.removeArticle(any())).thenReturn(Unit)
        `when`(articlesMapper.toData(any())).thenReturn(mockedArticleEntity)
        // Act
        underTest.removeArticles(mockedArticles)

        // Assertion/Verfication
        verify(articlesDao, Times(mockedArticles.size)).removeArticle(any())
    }

    @Test
    fun `underTest getAllArticlesWithIds calls from articleDao getArticlesWithIds with ids`(){
        val mockedArticleIds = List(12){it}
        `when`(articlesDao.getArticlesWithIds(mockedArticleIds)).thenReturn(flowOf(getMockArticleEntityList()))
        underTest.getAllArticlesWithIds(mockedArticleIds)
        verify(articlesDao).getArticlesWithIds(mockedArticleIds)
        verify(articlesMapper).toDomain(any())
    }

    private fun getMockArticleEntityList(count: Int = 10): List<ArticleEntity>{
        return List(count){
            mock(ArticleEntity::class.java)
        }
    }

    private fun getMockArticleNews(count: Int = 10): List<ArticleNews>{
        return List(count){
            mock(ArticleNews::class.java)
        }
    }

}