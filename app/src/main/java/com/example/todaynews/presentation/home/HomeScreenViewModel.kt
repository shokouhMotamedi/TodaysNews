package com.example.todaynews.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.domain.repository.NewsArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val repository: NewsArticleRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState())
    val state get() = _state.asStateFlow()

    init {
        loadArticles()
    }


    private fun loadArticles() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val articles = repository.getAllArticles()
            _state.update { it.copy(isLoading = false, articles = articles) }
        }
    }
}

class HomeViewModelFactory(
    private val repository: NewsArticleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreenViewModel(repository) as T
    }
}

data class ScreenState(
    val isLoading: Boolean = false,
    val articles: List<ArticleNews> = emptyList()
)