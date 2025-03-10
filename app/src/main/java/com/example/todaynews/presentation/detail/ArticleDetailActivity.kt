package com.example.todaynews.presentation.detail


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.todaynews.R
import com.example.todaynews.databinding.ActivityArticleDetailBinding
import dagger.hilt.android.AndroidEntryPoint

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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))


    }

}