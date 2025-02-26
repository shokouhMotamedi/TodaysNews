package com.example.todaynews.presentation.detail

import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.example.todaynews.domain.usecase.AddOrRemoveFromFavoriteUsecase
import com.example.todaynews.domain.usecase.GenericUsecaseResult
import com.example.todaynews.domain.usecase.GetArticleByIdUsercase
import com.example.todaynews.presentation.home.SavableArticle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "ArticleDetailsViewModel"

class ArticleDetailsViewModel(
    private val getArticleByIdUsercase: GetArticleByIdUsercase,
    private val addOrRemoveFromFavoriteUsecase: AddOrRemoveFromFavoriteUsecase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    val articleIdFlow = savedStateHandle.getStateFlow("ARTICLE_ID",-1)
    
    init {
        viewModelScope.launch { 
            articleIdFlow.collect {
                Log.d(TAG, "ArticleId: $it")
            }
        }
    }
    private var _eventChannel = Channel<ArticleDetailEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _state: MutableStateFlow<ArticleDetailsScreenState> =
        MutableStateFlow(ArticleDetailsScreenState())
    val state get() = _state.asStateFlow()
    


    private fun loadArticleFromSavedStateHandle() {
        savedStateHandle.getStateFlow<Int>("ARTICLE_ID", -1).onEach {
            Log.d(TAG, "loadArticleFromSavedStateHandle: ArticleID:$it")
            loadArticle(it)
        }.launchIn(viewModelScope)
    }

    private fun loadArticle(articleId: Int) {
        getArticleByIdUsercase.invoke(articleId)
            .onEach { usecaseResult ->
                when (usecaseResult) {
                    is GenericUsecaseResult.Error -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                        _eventChannel.send(
                            ArticleDetailEvent.ShowToast(
                                usecaseResult.error ?: "Something went wrong"
                            )
                        )
                    }

                    is GenericUsecaseResult.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is GenericUsecaseResult.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                article = usecaseResult.data
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ArticleDetailAction) {
        when (action) {
            is ArticleDetailAction.AddOrRemoveFavoriteDetail -> {
                viewModelScope.launch {
                    addOrRemoveFromFavoriteUsecase.invoke(action.article)
                }
            }

            is ArticleDetailAction.LoadArticle -> loadArticle(action.articleId)
            //loadArticleFromSavedStateHandle() //

        }
    }


}

class DetailViewModelFactory(
    private val getArticleByIdUsercase: GetArticleByIdUsercase,
    private val addOrRemoveFromFavoriteUsecase: AddOrRemoveFromFavoriteUsecase,
    private val owner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return ArticleDetailsViewModel(
            getArticleByIdUsercase,
            addOrRemoveFromFavoriteUsecase,
            handle
        ) as T
    }

}