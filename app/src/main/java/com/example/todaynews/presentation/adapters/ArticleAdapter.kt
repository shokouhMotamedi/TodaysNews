package com.example.todaynews.presentation.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.databinding.ItemRowBinding
import com.example.todaynews.presentation.home.SavableArticle

class ArticleAdapter(
    private val onNewsClicked: (SavableArticle) -> Unit,
    private val onAddToFavorite: (SavableArticle) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var articles: List<SavableArticle> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRowBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindArticle(articles[position])
    }

    fun submitData(articles: List<SavableArticle>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindArticle(articlesNews: SavableArticle) {
            binding.apply {
                titleHome.text = articlesNews.article.title
                descHome.text = articlesNews.article.description
                Glide.with(binding.root).load(articlesNews.article.imageResId).into(imgNews)
            }
            binding.root.setOnClickListener {
                onNewsClicked(articlesNews)
            }
            binding.AddToFavoritesHome.imageTintList = ColorStateList.valueOf(if (articlesNews.isFavorite) Color.RED else Color.BLACK)
            binding.AddToFavoritesHome.setOnClickListener {
                onAddToFavorite(articlesNews)
            }
        }
    }
}
