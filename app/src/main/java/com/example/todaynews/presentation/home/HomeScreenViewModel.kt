package com.example.todaynews.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.usecase.AddOrRemoveFromFavoriteUsecase
import com.example.todaynews.domain.usecase.GetSavableArticlesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getSavableArticleUserCase: GetSavableArticlesUseCase,
    private val addOrRemoveFromFavoriteUsecase: AddOrRemoveFromFavoriteUsecase
) : ViewModel() {
    // Single Shot Events
    private val _eventChannel = Channel<HomeScreenEvent>()
    val event = _eventChannel.receiveAsFlow()

    /*

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

        */

    val savableArticles = getSavableArticleUserCase.invoke()
        .map { value: Result<List<SavableArticle>> ->
            StateViewModel(
                isLoading = false,
                articles = value.getOrNull() ?: emptyList()
            )
        }
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(4000),StateViewModel(isLoading = true))


    fun onAction(action: HomeScreenAction){
        when(action){
            is HomeScreenAction.AddToFavorites -> handleAddToFavorite(action.article)
            is HomeScreenAction.OnNewsClicked -> handleClickOnNews(action.article)
        }
    }

    private fun handleAddToFavorite(articleNews: ArticleNews){
        viewModelScope.launch {
            addOrRemoveFromFavoriteUsecase.invoke(articleNews)
        }
    }

    private fun handleClickOnNews(articleNews: ArticleNews){
        viewModelScope.launch {
            TODO()
        }
    }
}

class HomeViewModelFactory(
    private val getArticlesUseCase: GetSavableArticlesUseCase,
    private val addOrRemoveFromFavoriteUsecase: AddOrRemoveFromFavoriteUsecase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreenViewModel(getArticlesUseCase, addOrRemoveFromFavoriteUsecase) as T
    }
}