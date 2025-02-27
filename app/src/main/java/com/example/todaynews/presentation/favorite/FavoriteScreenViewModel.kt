package com.example.todaynews.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.usecase.FavoriteUseCase
import com.example.todaynews.domain.usecase.RemoveFromFavorite
import com.example.todaynews.domain.usecase.UsecaseResult
import com.example.todaynews.presentation.home.FavoriteScreenAction
import com.example.todaynews.presentation.home.SavableArticle
import com.example.todaynews.presentation.home.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase,
    private val removeFromFavorite: RemoveFromFavorite
) : ViewModel() {


    val state = favoriteUseCase.invoke()
        .map { useCaseResult ->
            if (useCaseResult is UsecaseResult.Error){
                useCaseResult.articles
            }
            StateViewModel(
                isLoading = useCaseResult is UsecaseResult.Loading,
                articles = useCaseResult.articles.map { SavableArticle(it,isFavorite = true) }
            )
        }

    fun onAction(action: FavoriteScreenAction) {
        when (action) {
            is FavoriteScreenAction.RemoveFromFavoritePage -> removeFromFavorite(action.favoriteArticle)
        }
    }

    private fun removeFromFavorite(article: ArticleNews) {
        viewModelScope.launch {
            removeFromFavorite.invoke(article)
        }
    }

}

class FavoriteViewModelFactory(
    private val removeFromFavorite: RemoveFromFavorite,
    private val favoriteUseCase: FavoriteUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteScreenViewModel(favoriteUseCase, removeFromFavorite) as T
    }
}