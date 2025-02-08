package com.example.todaynews.data.remote

import com.google.gson.annotations.SerializedName

data class ArticlesList(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)