package com.example.todaynews.presentation.detail

sealed interface ArticleDetailEvent {
    data class ShowToast(val message: String): ArticleDetailEvent
}