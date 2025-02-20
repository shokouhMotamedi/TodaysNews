package com.example.todaynews.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynews.R
import com.example.todaynews.databinding.FragmentHomeBinding
import com.example.todaynews.di.DependencyContainer
import com.example.todaynews.presentation.adapters.ArticleAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private lateinit var articleAdapter: ArticleAdapter

    private val viewModel: HomeScreenViewModel by viewModels {
        DependencyContainer.homeViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        articleAdapter = ArticleAdapter(
            onNewsClicked = { articleNews ->
                findNavController().navigate(R.id.action_homeFragment_to_readNewsFragment)
            },
            onAddToFavorite = { articleNews ->
                viewModel.onAction(HomeScreenAction.AddToFavorites(articleNews.article))
            }
        )

        binding.rvListNews.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = articleAdapter
        }

        collectState()

        return binding.root
    }


    private fun collectState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.savableArticles.collectLatest { stateViewModel ->
                    articleAdapter.submitData(stateViewModel.articles)
                    binding.progressBar.isVisible = stateViewModel.isLoading
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.event.collect{ event ->
                    when(event){
                        HomeScreenEvent.NavigateToDetails -> {
                            //
                        }
                        is HomeScreenEvent.ShowSnacbarWithMessage -> {
                            Snackbar.make(binding.root,event.message,Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}