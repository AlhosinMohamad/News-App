package com.example.news_app_mvvm_sec20.presetation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.news_app_mvvm_sec20.data.Model.Article
import com.example.news_app_mvvm_sec20.databinding.NewsListItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
	
	// 1- DiffUtil class is used to calculate the difference between the old list and the new list
	// to know the minimum number of changes to convert one list into another
	//in order to not recreate all the views int the Recycler view
	private val callback= object: DiffUtil.ItemCallback<Article>(){
		override fun areItemsTheSame(oldItem : Article, newItem : Article) : Boolean {
			return oldItem.url == newItem.url
		}
		
		override fun areContentsTheSame(oldItem : Article, newItem : Article) : Boolean {
			return oldItem == newItem
		}
		
	}
	
	val differ=AsyncListDiffer(this,callback)
	
	//-----------------------------------------------------------
	
	// 2- Web View clickable list item Functionality
	private var onItemClickListener: ((Article)->Unit)?=null
	
	fun setOnItemClickListener(listener : (Article)->Unit){
		onItemClickListener = listener
	}
	//------------------------------------------------------------
	
	override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : NewsViewHolder {
		val binding= NewsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		
		return NewsViewHolder(binding)
	}
	
	override fun getItemCount() : Int {
		return differ.currentList.size//**
	}
	
	override fun onBindViewHolder(holder : NewsViewHolder, position : Int) {
		val article = differ.currentList[position]//**
		holder.bind(article)
	}
	
	inner class NewsViewHolder(
		val binding:NewsListItemBinding
	):ViewHolder(binding.root){
		
	   fun bind(article : Article){
		   binding.tvTitle.text= article.title
		   binding.tvDescription.text=article.description
		   binding.tvSource.text=article.source.name
		   binding.tvPublishedAt.text=article.publishedAt
		   
		   Glide.with(binding.ivArticleImage)
			   .load(article.urlToImage)
			   .into(binding.ivArticleImage)
		
		   // 2- Web View clickable list item Functionality
		   binding.root.setOnClickListener {
			   onItemClickListener?.let {
				   if(article!=null)
				      it(article)
			   }
		   }
		   
		   
	   }
	}
	
	
}
