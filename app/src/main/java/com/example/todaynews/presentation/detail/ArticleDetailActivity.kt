package com.example.todaynews.presentation.detail


import android.content.res.ColorStateList
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.todaynews.R
import com.example.todaynews.databinding.ActivityArticleDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleDetailActivity : AppCompatActivity() {

    /**
     * 1. Article Details -> Receive an article id as an intent extra.
     * 2. Push it to the ArticleDetailsViewModel
     * 3. Load the article up into the state
     *  a. GetArtcleById Usecase  -> using SavableS
     * 4. Collect the state
     * */

    private lateinit var _binding: ActivityArticleDetailBinding
    private val binding get() = _binding
    private val viewModel: ArticleDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        collectState()

    }

    private fun collectState() {
        lifecycleScope.launch {
            viewModel.state.collect { stateViewModel ->
                val article = stateViewModel.article
                article?.let { savableArticle ->
                    binding.titleRead.text = savableArticle.article.title
                    binding.descriptionRead.text = savableArticle.article.description
                    binding.favoriteRead.updateIconTInt(savableArticle.isFavorite)
                    Glide.with(this@ArticleDetailActivity).load(savableArticle.article.imageResId)
                        .into(binding.ivToolbarImage)

                }
            }
        }
        binding.favoriteRead.setOnClickListener {
            val article = viewModel.state.value.article
            article?.let {
                viewModel.onAction(ArticleDetailAction.AddOrRemoveFavoriteDetail(it.article))
            }
        }

    }

    private fun FloatingActionButton.updateIconTInt(isFavorite: Boolean) {
        val colorResId = if (isFavorite) R.color.red else R.color.black
        this.imageTintList = ColorStateList.valueOf(getColor(colorResId))
    }
}