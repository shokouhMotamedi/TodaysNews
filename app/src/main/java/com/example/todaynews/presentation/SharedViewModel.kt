package com.example.todaynews.presentation


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.todaynews.domain.model.ArticleNews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel: ViewModel() {

    private val _selectedArticle = MutableStateFlow<ArticleNews?>(null)
    val selectedArticle = _selectedArticle.asStateFlow()

     fun selectedArticle(articleNews: ArticleNews){
        Log.d("SharedViewModel", "shokouh - Article set: ${articleNews.title}")
        _selectedArticle.value = articleNews
        //_selectedArticle.emit(articleNews)
    }

}