package com.example.todaynews

import android.app.Application
import com.example.todaynews.di.DependencyContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DependencyContainer.init(this)
    }
}