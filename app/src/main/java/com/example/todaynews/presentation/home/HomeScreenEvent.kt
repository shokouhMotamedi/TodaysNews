package com.example.todaynews.presentation.home

sealed interface HomeScreenEvent {
    data class ShowSnacbarWithMessage(val message: String): HomeScreenEvent
    data class NavigateToDetails(val articleId: Int): HomeScreenEvent
}
