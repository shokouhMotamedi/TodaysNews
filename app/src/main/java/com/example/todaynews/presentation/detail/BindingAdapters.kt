package com.example.todaynews.presentation.detail

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.todaynews.R
import com.example.todaynews.presentation.home.SavableArticle
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("isVisible")
fun isVisible(view: View,isVisible: Boolean){
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("title")
fun articleTitle(textView: TextView, savableArticle: SavableArticle?){
    savableArticle?.let {
        textView.text = it.article.title
    }
}

@BindingAdapter("description")
fun articleDescription(textView: TextView, savableArticle: SavableArticle?){
    savableArticle?.let {
        textView.text = it.article.description
    }
}

@BindingAdapter("image")
fun articleImage(imageView: ImageView, savableArticle: SavableArticle?){
    savableArticle?.let {
        Glide.with(imageView.context).load(savableArticle.article.imageResId).into(imageView)
    }
}

@BindingAdapter("favorite")
fun favoriteFloatBtn(floatingActionButton : FloatingActionButton, isFavorite : Boolean?){
    val colorResId = if (isFavorite == true) R.color.red else R.color.black
    floatingActionButton.imageTintList = ColorStateList.valueOf(floatingActionButton.context.getColor(colorResId))
}