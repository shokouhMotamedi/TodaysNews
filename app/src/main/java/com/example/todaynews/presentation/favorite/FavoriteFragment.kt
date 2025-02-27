package com.example.todaynews.presentation.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todaynews.databinding.FragmentFavoriteBinding
import com.example.todaynews.di.DependencyContainer
import com.example.todaynews.presentation.adapters.FavoriteAdapter
import com.example.todaynews.presentation.detail.ArticleDetailActivity
import com.example.todaynews.presentation.home.FavoriteScreenAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var  binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    private val viewModel: FavoriteScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        favoriteAdapter = FavoriteAdapter(
            onArticleClicked = {  savableArticle ->
                val intent = Intent(requireContext(), ArticleDetailActivity::class.java).apply {
                    putExtra("ARTICLE_ID", savableArticle.article.id)
                }
                startActivity(intent)
            },
            onRemoveFromFavorite = { savableArticle ->
                val favoriteArticle = savableArticle.article
                viewModel.onAction(FavoriteScreenAction.RemoveFromFavoritePage(favoriteArticle))
            }
        )

        binding.rvListNewsFavorite.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = favoriteAdapter
        }

        loadDataFavorite()

        return binding.root
    }

    private fun loadDataFavorite() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { stateViewModel ->
              favoriteAdapter.submitFavorite(stateViewModel.articles)
            }
        }
    }


}