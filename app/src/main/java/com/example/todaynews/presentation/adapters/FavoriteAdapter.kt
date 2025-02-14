package com.example.todaynews.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynews.databinding.ItemArticleFavoriteBinding
import com.example.todaynews.domain.model.ArticleNews

class FavoriteAdapter(
    private val onArticleClicked: (ArticleNews) -> Unit,
    private val onRemoveFromFavorite: (ArticleNews) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var article: List<ArticleNews> = emptyList()

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
    fun submitFavorite(articles: List<ArticleNews>){
        this.article = articles
        notifyDataSetChanged()
    }


    inner class FavoriteViewHolder(private val binding: ItemArticleFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindFavorites(articleNews: ArticleNews) {
            binding.apply {
                tvArticleTitleFavorite.text = articleNews.title
                Glide.with(binding.root).load(articleNews.imageResId).into(imgArticleFavorite)
            }
            binding.root.setOnClickListener {
                onArticleClicked(articleNews)
            }
            binding.btnRemoveFavorite.setOnClickListener {
                onRemoveFromFavorite(articleNews)
            }

        }
    }
}