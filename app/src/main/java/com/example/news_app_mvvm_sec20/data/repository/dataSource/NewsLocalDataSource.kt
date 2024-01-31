package com.example.news_app_mvvm_sec20.data.repository.dataSource

import com.example.news_app_mvvm_sec20.data.Model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
	
	suspend fun saveArticleToDB(article : Article)
	
	suspend fun deleteArticleFromDB(article:Article)
	
	fun getSavedArticles(): Flow<List<Article>>// always functions that return livedata or flow cant be suspend
	                                           // because we want to get all articles together in one time without pause and resume
}