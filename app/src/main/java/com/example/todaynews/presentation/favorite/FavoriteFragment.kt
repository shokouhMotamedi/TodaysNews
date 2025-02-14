package com.example.todaynews.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynews.R
import com.example.todaynews.databinding.FragmentFavoriteBinding
import com.example.todaynews.di.DependencyContainer
import com.example.todaynews.presentation.adapters.FavoriteAdapter
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
            onArticleClicked = { articlNews ->
                //?
                findNavController().navigate(R.id.action_favoriteFragment_to_readNewsFragment)
            },
            onRemoveFromFavorite = { articleNews ->
                viewModel.removeFromFavorite(articleNews.id)
            }
        )

        binding.rvListNewsFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }

        loadDataFavorite()

        return binding.root
    }

    private fun loadDataFavorite() {
        lifecycleScope.launch {
//            viewModel.state.collectLatest { state ->
//                favoriteAdapter.submitFavorite(state.articles)
//            }
        }
    }


}