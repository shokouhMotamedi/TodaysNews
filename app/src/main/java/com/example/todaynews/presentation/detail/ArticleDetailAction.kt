package com.example.todaynews.presentation.detail

import com.example.todaynews.domain.model.ArticleNews

interface ArticleDetailAction {
    data class AddOrRemoveFavoriteDetail(val article: ArticleNews) : ArticleDetailAction
}