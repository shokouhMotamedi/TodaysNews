package com.example.todaynews.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynews.R
import com.example.todaynews.data.remote.ArticlesApiService
import com.example.todaynews.databinding.FragmentHomeBinding
import com.example.todaynews.domain.repository.NewsArticleRepositoryImpl
import com.example.todaynews.presentation.adapters.ArticleAdapter
import com.example.todaynews.presentation.SharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var articleAdapter: ArticleAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val viewModel: HomeScreenViewModel by viewModels {
        HomeViewModelFactory(NewsArticleRepositoryImpl(ArticlesApiService.getArticlesApiService()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        articleAdapter = ArticleAdapter(
            onNewsClicked = { articleNews ->
                sharedViewModel.selectedArticle(articleNews)
                findNavController().navigate(R.id.action_homeFragment_to_readNewsFragment)
            }
        )

        binding.rvListNews.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = articleAdapter
        }

        fetchArticles()

        return binding.root
    }

    private fun fetchArticles() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                articleAdapter.submitData(state.articles)
                binding.progressBar.isVisible = state.isLoading
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}