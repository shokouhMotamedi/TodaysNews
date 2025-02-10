package com.example.todaynews.data.remote

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val APIKEY = "e7008494088f41a3819bc79a4c7eb38a"

interface ArticlesApiService {

    @GET("v2/top-headlines?country=us&category=business&apiKey=$APIKEY")
    suspend fun fetchAllArticles(): Response<ArticlesList>

    companion object{
        // val client = OkHttpClient.Builder().build()
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)") // ✅ Pretend to be a real browser
                    .header("Accept", "application/json") // ✅ Force JSON response
                    .header("Connection", "close") // ✅ Prevent keep-alive issues
                    .build()
                chain.proceed(request)
            }
            .build()

        private fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org/")
                .client(client)
                .build()
        }

        fun getArticlesApiService() : ArticlesApiService{
            return  getInstance().create(ArticlesApiService::class.java)
        }
    }
}