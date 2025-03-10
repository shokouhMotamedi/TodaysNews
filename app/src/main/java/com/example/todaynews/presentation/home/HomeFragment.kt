package com.example.todaynews.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynews.databinding.FragmentHomeBinding
import com.example.todaynews.presentation.adapters.ArticleAdapter
import com.example.todaynews.presentation.detail.ArticleDetailActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private lateinit var articleAdapter: ArticleAdapter

    private val viewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        articleAdapter = ArticleAdapter(
            onNewsClicked = { savableArticle ->
               viewModel.onAction(HomeScreenAction.OnArticleClicked(savableArticle.article))
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
                        is HomeScreenEvent.ShowSnacbarWithMessage -> {
                            Snackbar.make(binding.root,event.message,Snackbar.LENGTH_SHORT).show()
                        }

                        is HomeScreenEvent.NavigateToDetails -> {
                            val intent = Intent(requireContext(), ArticleDetailActivity::class.java).apply {
                                putExtra("ARTICLE_ID",event.articleId)
                            }
                            startActivity(intent)

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