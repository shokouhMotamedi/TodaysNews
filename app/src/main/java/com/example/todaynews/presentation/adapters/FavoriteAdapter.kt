package com.example.todaynews.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynews.databinding.ItemArticleFavoriteBinding
import com.example.todaynews.presentation.home.SavableArticle

class FavoriteAdapter(
    private val onArticleClicked: (SavableArticle) -> Unit,
    private val onRemoveFromFavorite: (SavableArticle) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var article: List<SavableArticle> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemArticleFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return article.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindFavorites(article[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitFavorite(articles: List<SavableArticle>){
        this.article = articles
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemArticleFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindFavorites(savableArticle: SavableArticle) {
            binding.apply {
                tvArticleTitleFavorite.text = savableArticle.article.title
                Glide.with(binding.root).load(savableArticle.article.imageResId).into(imgArticleFavorite)
            }
            binding.root.setOnClickListener {
                onArticleClicked(savableArticle)
            }
            binding.btnRemoveFavorite.setOnClickListener {
                onRemoveFromFavorite(savableArticle)
            }

        }
    }
}