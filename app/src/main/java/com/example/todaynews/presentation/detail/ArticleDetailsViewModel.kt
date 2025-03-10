package com.example.todaynews.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.usecase.AddOrRemoveFromFavoriteUsecase
import com.example.todaynews.domain.usecase.GetArticleByIdUsercase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val getArticleByIdUsercase: GetArticleByIdUsercase,
    private val addOrRemoveFromFavoriteUsecase: AddOrRemoveFromFavoriteUsecase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()

    val savableArticle = savedStateHandle.getStateFlow("ARTICLE_ID",-1)
        .flatMapConcat { articleId ->
            getArticleByIdUsercase.invoke(articleId)
        }
        .map { result ->
            _isLoading.value = false
            result.data
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),null)

    fun addOrRemoveFromFavorite(article: ArticleNews){
        viewModelScope.launch {
            addOrRemoveFromFavoriteUsecase.invoke(article)
        }
    }


}


/**
 *********
 *
 *     // When is 1 flow,  which we want to use as an input of other flow
 *     val state = savedStateHandle.getStateFlow("ARTICLE_ID",-1)
 *         .flatMapConcat { articleId ->
 *             getArticleByIdUsercase.invoke(articleId)
 *         }
 *         .map { result ->
 *             ArticleDetailsScreenState(
 *                 article = result.data,
 *                 isLoading = false
 *             )
 *         }
 *         .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),ArticleDetailsScreenState())
 *
 *
 *********
 *
 * private val _state: MutableStateFlow<ArticleDetailsScreenState> =
 *         MutableStateFlow(ArticleDetailsScreenState())
 *     val state get() = _state.asStateFlow()
 *
 * private fun loadArticle(articleId: Int) {
 *         getArticleByIdUsercase.invoke(articleId)
 *             .onEach { usecaseResult ->
 *                 when (usecaseResult) {
 *                     is GenericUsecaseResult.Error -> {
 *                         _state.update {
 *                             it.copy(isLoading = true)
 *                         }
 *                         _eventChannel.send(
 *                             ArticleDetailEvent.ShowToast(
 *                                 usecaseResult.error ?: "Something went wrong"
 *                             )
 *                         )
 *                     }
 *
 *                     is GenericUsecaseResult.Loading -> {
 *                         _state.update {
 *                             it.copy(isLoading = true)
 *                         }
 *                     }
 *
 *                     is GenericUsecaseResult.Success -> {
 *                         _state.update {
 *                             it.copy(
 *                                 isLoading = false,
 *                                 article = usecaseResult.data
 *                             )
 *                         }
 *                     }
 *                 }
 *             }
 *             .launchIn(viewModelScope)
 *     }
 */