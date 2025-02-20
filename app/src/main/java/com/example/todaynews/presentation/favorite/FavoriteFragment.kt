package com.example.todaynews.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todaynews.R
import com.example.todaynews.databinding.FragmentFavoriteBinding
import com.example.todaynews.di.DependencyContainer
import com.example.todaynews.presentation.adapters.FavoriteAdapter
import com.example.todaynews.presentation.home.FavoriteScreenAction
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private lateinit var  binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    private val viewModel: FavoriteScreenViewModel by viewModels {
        DependencyContainer.favoriteViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        favoriteAdapter = FavoriteAdapter(
            onArticleClicked = {
                findNavController().navigate(R.id.action_favoriteFragment_to_readNewsFragment)
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