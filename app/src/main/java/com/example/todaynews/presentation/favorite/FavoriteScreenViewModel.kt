package com.example.todaynews.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todaynews.domain.repository.article.NewsArticleRepository
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(
    repository: NewsArticleRepository
) : ViewModel() {


    fun removeFromFavorite(int: Int){
        viewModelScope.launch {
          // repository.removeFromFavorite(int)
        }
    }




    /*
    *

    *
    *

private val _state = MutableStateFlow(StateViewModel())
 val state get() = _state.asStateFlow()


    private fun loadArticlesInFavorite(){
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val articleFlow = favoritesRepository.getFavorites()
            articleFlow.onEach {articlesNews ->
                _state.update { it.copy(isLoading = false, articles = articlesNews) }
            }.launchIn(viewModelScope)
            /*
                or
             */
            articleFlow.collect{ articles ->
                _state.update { it.copy(isLoading = false, articles = articles) }
            }
        }
    }
     */
}


class FavoriteViewModelFactory(
    private val newsArticleRepository: NewsArticleRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteScreenViewModel(newsArticleRepository) as T

    }
}