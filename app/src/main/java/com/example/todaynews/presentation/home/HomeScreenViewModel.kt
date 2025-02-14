package com.example.todaynews.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.repository.article.NewsArticleRepository
import com.example.todaynews.domain.usecase.GetArticlesUseCase
import com.example.todaynews.domain.usecase.UsecaseResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val repository: NewsArticleRepository,
    private val getArticlesUseCase: GetArticlesUseCase,
) : ViewModel() {
    // Single Shot Events
    private val _eventChannel = Channel<HomeScreenEvent>()
    val event = _eventChannel.receiveAsFlow()

    val state = getArticlesUseCase.invoke()
        .map { usecaseResult ->
            if (usecaseResult is UsecaseResult.Error){
                _eventChannel.send(HomeScreenEvent.ShowSnacbarWithMessage(usecaseResult.error ?: "Something went wrong"))
            }
            StateViewModel(
                isLoading = usecaseResult is UsecaseResult.Loading,
                articles = usecaseResult.articles.map { SavableArticle(it,isFavorite = true) }
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(4000),StateViewModel(isLoading = true))

    val combinedFlow = combine(getArticlesUseCase.invoke(),repository.getFavorites()){ usecaseResult, favoriteIds ->
        if (usecaseResult is UsecaseResult.Error){
            _eventChannel.send(HomeScreenEvent.ShowSnacbarWithMessage(usecaseResult.error ?: "Something went wrong"))
        }
        val savableArticles = usecaseResult.articles.map {
            SavableArticle(
                article = it,
                isFavorite = favoriteIds.contains(it.id)
            )
        }
        StateViewModel(
            isLoading = usecaseResult is UsecaseResult.Loading,
            articles = savableArticles
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(4000),StateViewModel(isLoading = true))

    fun onAction(action: HomeScreenAction){
        when(action){
            is HomeScreenAction.AddToFavorites -> handleAddToFavorite(action.article)
        }
    }
    private suspend fun isFavorite(articleNews: ArticleNews): Boolean {
        val favoriteArticleIds = repository.getFavorites().first()
        return favoriteArticleIds.contains(articleNews.id)
    }
    private fun handleAddToFavorite(articleNews: ArticleNews){
        viewModelScope.launch {
            if (isFavorite(articleNews)){
                removeFromFavorites(articleNews)
                return@launch
            }
            addToFavorites(articleNews)
        }
    }

    private suspend fun addToFavorites(articleNews: ArticleNews){
        repository.addToFavorite(articleNews)
    }

    private suspend fun removeFromFavorites(articleNews: ArticleNews){
        repository.removeFromFavorite(articleNews)
    }


}

class HomeViewModelFactory(
    private val repository: NewsArticleRepository,
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreenViewModel(repository, getArticlesUseCase) as T
    }
}