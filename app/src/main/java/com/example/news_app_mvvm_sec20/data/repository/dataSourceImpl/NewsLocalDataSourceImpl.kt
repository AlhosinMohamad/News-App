package com.example.news_app_mvvm_sec20.data.repository.dataSourceImpl

import com.example.news_app_mvvm_sec20.data.Model.Article
import com.example.news_app_mvvm_sec20.data.db.ArticleDAO
import com.example.news_app_mvvm_sec20.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
	private val articleDAO : ArticleDAO
): NewsLocalDataSource {
	override suspend fun saveArticleToDB(article : Article) {
		articleDAO.insert(article)
	}
	
	override suspend fun deleteArticleFromDB(article : Article) {
		articleDAO.deleteArticle(article)
	}
	
	override fun getSavedArticles() : Flow<List<Article>> {
		return articleDAO.getAllArticles()// because we want to get all articles together in one time without pause and resume
	}
}