package com.example.news_app_mvvm_sec20

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news_app_mvvm_sec20.databinding.FragmentSavedNewsBinding
import com.example.news_app_mvvm_sec20.presetation.adapter.NewsAdapter
import com.example.news_app_mvvm_sec20.presetation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class savedNewsFragment : Fragment() {
	private lateinit var fragmentSavedNewsBinding:FragmentSavedNewsBinding
	private lateinit var viewModel: NewsViewModel
	private lateinit var savedNewsadapter:NewsAdapter
	
	override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		fragmentSavedNewsBinding= FragmentSavedNewsBinding.bind(view)
		viewModel=(activity as MainActivity).viewModel
		savedNewsadapter=(activity as MainActivity).newsAdapter//we use the same Adapter of the News Adapter
		
		// 1- Web View clickable list item Functionality: to read the article in a web view
		savedNewsadapter.setOnItemClickListener {
			try {
				val bundle = Bundle().apply {
					if (it != null) {
						putSerializable("selected_article", it)//put the clicked article in bundle and send it as Serializable
					}
				}
				if (bundle.size()>0){
					findNavController().navigate(
						R.id.action_savedNewsFragment_to_infoFragment,
						bundle
					)
				}
				
				
			}catch (e:Exception){
				Log.i("MYTAG",e.stackTraceToString().toString())
				Toast.makeText(activity,"Error", Toast.LENGTH_LONG).show()
			}
			
		}
		
		
		//2- initailize Recycler View
		initRecyclerView()
		//-------------
		
		/* 3- observe the livedata */
		viewModel.getSavedNews().observe(viewLifecycleOwner) {
			savedNewsadapter.differ.submitList(it)
		}
		//-------------
		
		//4- Delete Saved Article and Undo the Deletion Functionality
		val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
			ItemTouchHelper.UP or ItemTouchHelper.DOWN,
			ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
		){
			override fun onMove(
				recyclerView : RecyclerView,
				viewHolder : RecyclerView.ViewHolder,
				target : RecyclerView.ViewHolder
			) : Boolean {
				return true
			}
			// when Swiped delete it the show a Snack bar with Undo button to Undo The Deletion
			override fun onSwiped(viewHolder : RecyclerView.ViewHolder, direction : Int) {
				val position = viewHolder.adapterPosition
				val article= savedNewsadapter.differ.currentList[position]//1- get the Article at the current position
				
				viewModel.deleteArticle(article)// 2- Delete The Article at the current position
				
				Snackbar.make(view, "Deleted Successfully",Snackbar.LENGTH_LONG)//3- show a Snack bar with Undo button
					.apply {
						setAction("UNDO"){
							viewModel.saveNewstoDB(article)
						}
					}.show()
				
			}
		}
		
		ItemTouchHelper(itemTouchHelperCallback).apply { //so important to attach the item above with the RecyclerView to activate it.
			attachToRecyclerView(fragmentSavedNewsBinding.rvSavedNews)
		}
		//-----------------------
		
	}
	
	private fun initRecyclerView(){
		// newsAdapter= NewsAdapter(), it is better to use Hilt DI Here.
		fragmentSavedNewsBinding.rvSavedNews.apply {
			adapter= savedNewsadapter
			layoutManager= LinearLayoutManager(activity)
			
		}
	}
	
	override fun onCreateView(
		inflater : LayoutInflater, container : ViewGroup?,
		savedInstanceState : Bundle?
	) : View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_saved_news, container, false)
	}
	
}