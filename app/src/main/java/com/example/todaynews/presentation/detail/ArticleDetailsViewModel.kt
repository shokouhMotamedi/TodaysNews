package com.example.todaynews.presentation.detail

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.example.todaynews.domain.usecase.AddOrRemoveFromFavoriteUsecase
import com.example.todaynews.domain.usecase.GetArticleByIdUsercase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ArticleDetailsViewModel"
@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val getArticleByIdUsercase: GetArticleByIdUsercase,
    private val addOrRemoveFromFavoriteUsecase: AddOrRemoveFromFavoriteUsecase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // When is 1 flow,  which we want to use as an input of other flow
    val state = savedStateHandle.getStateFlow("ARTICLE_ID",-1)
        .flatMapConcat { articleId ->
            getArticleByIdUsercase.invoke(articleId)
        }
        .map { result ->
            ArticleDetailsScreenState(
                article = result.data,
                isLoading = false
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),ArticleDetailsScreenState())


    fun onAction(action: ArticleDetailAction) {
        when (action) {
            is ArticleDetailAction.AddOrRemoveFavoriteDetail -> {
                viewModelScope.launch {
                    addOrRemoveFromFavoriteUsecase.invoke(action.article)
                }
            }
        }
    }


}

class DetailViewModelFactory(
    private val getArticleByIdUsercase: GetArticleByIdUsercase,
    private val addOrRemoveFromFavoriteUsecase: AddOrRemoveFromFavoriteUsecase,
    owner: SavedStateRegistryOwner
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

/**
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