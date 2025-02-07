package com.example.todaynews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaynews.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        articleAdapter = ArticleAdapter()
        binding?.rvListNews?.apply { layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = articleAdapter
        }
        load()

        return binding?.root
    }

    private fun load(){
        // Sample Data
        val articles = listOf(
            ArticleNews(1, "Breaking News", "This is the first news article.", R.drawable.ic_sports_ighlight),
            ArticleNews(2, "Technology Update", "Latest advancements in AI.", R.drawable.ic_sports_ighlight),
            ArticleNews(3, "Sports Highlights", "Today's top sports events.",  R.drawable.ic_sports_ighlight ),
            ArticleNews(4,  "Health & Wellness", "Tips for a healthier life.", R.drawable.ic_sports_ighlight),
            ArticleNews(5,  "Entertainment", "Upcoming movies and shows.", R.drawable.ic_sports_ighlight)
        )
        articleAdapter.submitData(articles)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}