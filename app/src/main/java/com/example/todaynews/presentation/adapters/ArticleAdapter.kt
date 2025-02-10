package com.example.todaynews.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaynews.domain.model.ArticleNews
import com.example.todaynews.databinding.ItemRowBinding

class ArticleAdapter(
    private val onNewsClicked: (ArticleNews) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var articles: List<ArticleNews> = emptyList()

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

    fun submitData(articles: List<ArticleNews>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindArticle(articlesNews: ArticleNews) {
            binding.apply {
                titleHome.text = articlesNews.title
                descHome.text = articlesNews.description
                Glide.with(binding.root).load(articlesNews.imageResId).into(imgNews)
            }
            binding.root.setOnClickListener {
                onNewsClicked(articlesNews)
            }
        }
    }
}
