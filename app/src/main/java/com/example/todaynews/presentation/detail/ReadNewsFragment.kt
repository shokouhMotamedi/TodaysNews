package com.example.todaynews.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.todaynews.databinding.FragmentReadNewsBinding
import com.example.todaynews.presentation.SharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ReadNewsFragment : Fragment() {

    private var _binding: FragmentReadNewsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadNewsBinding.inflate(inflater,container,false)
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            sharedViewModel.selectedArticle.collectLatest { articleNews ->
                Log.d("ReadNewsFragment", "shokouh - Received article: ${articleNews?.title}")
                if(articleNews != null){
                    binding.articleTitle.text = articleNews.title
                    binding.articleDescription.text = articleNews.description
                    Glide.with(requireContext()).load(articleNews.imageResId).into(binding.articleImage)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}