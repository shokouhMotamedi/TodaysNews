package com.example.todaynews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynews.data.remote.ArticlesApiService
import com.example.todaynews.databinding.FragmentHomeBinding
import com.example.todaynews.domain.repository.NewsArticleRepositoryImpl
import com.example.todaynews.presentation.ArticleAdapter
import com.example.todaynews.presentation.HomeScreenViewModel
import com.example.todaynews.presentation.HomeViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var articleAdapter: ArticleAdapter

    private val viewModel: HomeScreenViewModel by viewModels {
        HomeViewModelFactory(NewsArticleRepositoryImpl(ArticlesApiService.getArticlesApiService()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        articleAdapter = ArticleAdapter()
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