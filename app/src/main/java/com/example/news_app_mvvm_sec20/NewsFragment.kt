package com.example.news_app_mvvm_sec20

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.news_app_mvvm_sec20.data.util.Resource
import com.example.news_app_mvvm_sec20.databinding.FragmentNewsBinding
import com.example.news_app_mvvm_sec20.presetation.adapter.NewsAdapter
import com.example.news_app_mvvm_sec20.presetation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {
	// Essential Parameters
	private lateinit var viewModel : NewsViewModel
	private lateinit  var fragmentsNewsBinding: FragmentNewsBinding
	private lateinit var newsAdapter:NewsAdapter
	
	// Query Api Parameters
	private val country="us"
	private var page=1
	private var searchQuery=""
	
	// Paging Functionality parameters
	private var isScrolling= false
	private var isLoading= false
	private var isLastPage= false
	private var recycler_pages= 0
	
	// we use onViewCreated to build viewModel and FragmentBinding
	// because this function will execute only and after all View were Created Properly
	override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		// Defining the Essential Parameters
		viewModel=(activity as MainActivity).viewModel
		fragmentsNewsBinding= FragmentNewsBinding.bind(view)
		newsAdapter=(activity as MainActivity).newsAdapter//get Injected NewsAdapter from MainActivity
		
		// 2- Web View clickable list item Functionality: to read the article in a web view
		newsAdapter.setOnItemClickListener {
			try {
				val bundle = Bundle().apply {
					if (it != null) {
						putSerializable("selected_article", it)//put the clicked article in bundle and send it as Serializable
					}
				}
				if (bundle.size()>0){
					findNavController().navigate(
						R.id.action_newsFragment_to_infoFragment,
						bundle
					)
				}
					
				
			}catch (e:Exception){
				Log.i("MYTAG",e.stackTraceToString().toString())
				Toast.makeText(activity,"Error",Toast.LENGTH_LONG).show()
			}
			
		}
		
		//3- initialisation of the Recycler View,
		//4- and then Call the api for the Items and attach them with the recycler View
		initRecyclerView()
		viewNewsList()
		
		//5- set the search Functionality
		setSearchView()
		
	}
	//1- Recycler View Initialization
	private fun initRecyclerView(){
		// newsAdapter= NewsAdapter(), it is better to use Hilt DI Here.
		fragmentsNewsBinding.rvNews.apply {
			adapter= newsAdapter
			layoutManager= LinearLayoutManager(activity)
			
			// Paging Functionality, add the srcoole listner to the recycler View
			addOnScrollListener(this@NewsFragment.onSrollListner)
		}
		
	}
	//2- Show Top Head Lines List
	private fun viewNewsList(){
		viewModel.getNewsHeadLines(country,page)//2.1- get the List of news and add it to the observable List newsHeadlines
		
		//2.2- observe the newsHeadLines list and show it by giving it to the adapter
		viewModel.newsHeadLines.observe(viewLifecycleOwner) { response ->
			when (response) {
				is Resource.Success -> {
					hideProgressBar()
					response.data?.let {
						newsAdapter.differ.submitList(it.articles.toList())
						
						// Paging Functionality, Calculate the page
						if( it.totalResults%20 == 0){// api result is prime number
							recycler_pages = it.totalResults/20 // 20 is chunk size that come from API, so number of the page is quantity of Api result divided by 20.
						}else{
							recycler_pages = it.totalResults/20 + 1
						}
						isLastPage = page == recycler_pages// if true then we are in the Last page
					}
				}
				
				is Resource.Loading -> {
					showProgressBar()
				}
				
				is Resource.Error -> {
					hideProgressBar()
					response.message.let {
						Toast.makeText(activity,"An Error occurred: $it",Toast.LENGTH_LONG).show()
					}
				}
			}
		}
	}
	
	
	
	//3- Searched News List
	//3.1 set Search Settings
	private fun setSearchView(){
		//3.1.1- if the user click on search button and type something
		fragmentsNewsBinding.svNews.setOnQueryTextListener(
			object: SearchView.OnQueryTextListener{
				// 3.1.1.1- if the text in the search button completed and submitted
				override fun onQueryTextSubmit(searchQuery : String?) : Boolean {
					viewModel.getSearchedNews(country,page,searchQuery.toString())
					viewSearchedNews()
					return false
				}
				
				// 3.1.1.2- if the text in the search button changed
				override fun onQueryTextChange(newQuery : String?) : Boolean {
					//it is not efficient to call the function to get the searched query directly when the use type something so,
					//we will make a little delay to give the time to type something meaningful and after that we get the results of the searched query
					//for delay we will use coroutines with Main scope
					MainScope().launch {
						delay(2000)//2 seconds delay
						viewModel.getSearchedNews(country,page,newQuery.toString())
						viewSearchedNews()
					}
					return false
				}
				
			})
		
		//3.2 if the use click on close button(->) without search anything
		fragmentsNewsBinding.svNews.setOnCloseListener (
			object:SearchView.OnCloseListener{
				override fun onClose() : Boolean {
					initRecyclerView() //1- we recall the initialization of Recyclerview again
					viewNewsList() //2- we bring the News List and added the Recycler View to Show it.
					return false
				}
				
			})
		
	}
	
	private fun viewSearchedNews(){
		viewModel.searchedNews.observe(viewLifecycleOwner) { response ->
			when (response) {
				is Resource.Success -> {
					hideProgressBar()
					response.data?.let {
						newsAdapter.differ.submitList(it.articles.toList())
						
						// Paging Functionality, Calculate the page
						if( it.totalResults%20 == 0){// api result is prime number
							recycler_pages = it.totalResults/20 // 20 is chunk size that come from API, so number of the page is quantity of Api result divided by 20.
						}else{
							recycler_pages = it.totalResults/20 + 1
						}
						isLastPage = page == recycler_pages// if true then we are in the Last page
					}
				}
				is Resource.Loading -> {
					showProgressBar()
				}
				
				is Resource.Error -> {
					hideProgressBar()
					response.message.let {
						Toast.makeText(activity,"An Error occurred: $it",Toast.LENGTH_LONG).show()
					}
				}
			}
		}
		
	}
	//-------------------End Search Functionality
	
	//4- Progress Bar Show & Hide
	private fun showProgressBar(){
		isLoading=true // Paging Functionality
		fragmentsNewsBinding.newsProgressBar.visibility=View.VISIBLE
	}
	
	private fun hideProgressBar(){
		isLoading=false // Paging Functionality
		fragmentsNewsBinding.newsProgressBar.visibility=View.INVISIBLE
	}
	//-------------------
	
	//5- Paging Functionality
	private val onSrollListner= object :RecyclerView.OnScrollListener(){
		
		override fun onScrollStateChanged(recyclerView : RecyclerView, newState : Int) {
			super.onScrollStateChanged(recyclerView, newState)
			// Paging Functionality
			if(newState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
				isScrolling=true
				//we use this function to make sure that the Recycler View is Scrolled the we edite isScrolled Value to true
			}
		}
		
		override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {
			super.onScrolled(recyclerView, dx, dy)
			
			// Paging Functionality
			val layoutManager = fragmentsNewsBinding.rvNews.layoutManager as LinearLayoutManager
			
			val sizeOfTheCurrentList= layoutManager.itemCount
			val visibleItems = layoutManager.childCount
			val topPosition =layoutManager.findFirstVisibleItemPosition()
			
			val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
			val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
			
			if(shouldPaginate){
				page++
				viewModel.getNewsHeadLines(country,page)
				isScrolling = false
			}
			
		}
	}
	//------------------- End Paging Functionality
	
	
	override fun onCreateView(
		inflater : LayoutInflater, container : ViewGroup?,
		savedInstanceState : Bundle?
	) : View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_news, container, false)
	}
	
	
}