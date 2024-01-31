package com.example.news_app_mvvm_sec20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.news_app_mvvm_sec20.databinding.ActivityMainBinding
import com.example.news_app_mvvm_sec20.presetation.adapter.NewsAdapter
import com.example.news_app_mvvm_sec20.presetation.viewmodel.NewsViewModel
import com.example.news_app_mvvm_sec20.presetation.viewmodel.NewsViewModelFactory
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint// Hilt to inject the view model we need to inject the viewModelFactory first
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    
    // we are gonna implement: Single Activity Multiple Fragments,
    // so we make single viewModel and share it with all the Fragments
    @Inject//inject the viewModelFactory
    lateinit var newsViewModelFactory : NewsViewModelFactory
    lateinit var viewModel:NewsViewModel
    
    @Inject//inject the NewsAdapter for RecyclerView
    lateinit var newsAdapter: NewsAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //1- Set View Binding
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        //2- Set Fragment settings
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController= navHostFragment.navController
        
        binding.bnvNews.setupWithNavController(
            navController
        )
        
        //3- set model View
        viewModel=ViewModelProvider(this,newsViewModelFactory)
            .get(NewsViewModel::class.java)
    }
}