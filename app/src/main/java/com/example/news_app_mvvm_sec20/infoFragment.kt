package com.example.news_app_mvvm_sec20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.news_app_mvvm_sec20.databinding.FragmentInfoBinding
import com.example.news_app_mvvm_sec20.presetation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class infoFragment : Fragment() {
	private lateinit var fragmentInfoBinding: FragmentInfoBinding
	
	private lateinit var newsViewModel : NewsViewModel
	override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		fragmentInfoBinding= FragmentInfoBinding.bind(view)
		
		newsViewModel=(activity as MainActivity).viewModel//get Injected viewModel from Main Activity
		
		val args : infoFragmentArgs by navArgs()//get Argument from the last Fragment which is an Article Object
		
		val article = args.selectedArticle//get The Article object from args
		
		//1- Load the Article in Web View
		fragmentInfoBinding.webViewInfo.apply {
			webViewClient= WebViewClient()
			if(article.url!=null)
			   loadUrl(article.url)
			
		}
		
		//2- Save the Article when click on Floating Action Button
		fragmentInfoBinding.fabSaveInfo.setOnClickListener {
			newsViewModel.saveNewstoDB(article)
			Snackbar.make(view,"Saved Successfully", Snackbar.LENGTH_LONG).show()
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
	
	override fun onCreateView(
		inflater : LayoutInflater, container : ViewGroup?,
		savedInstanceState : Bundle?
	) : View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_info, container, false)
	}
	
	
	
}