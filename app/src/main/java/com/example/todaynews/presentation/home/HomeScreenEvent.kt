package com.example.todaynews.presentation.home

sealed interface HomeScreenEvent {
    data class ShowSnacbarWithMessage(val message: String): HomeScreenEvent
    data object NavigateToDetails: HomeScreenEvent
}
