package com.example.news_app_mvvm_sec20.domain.usacase

import com.example.news_app_mvvm_sec20.data.Model.Api_Response
import com.example.news_app_mvvm_sec20.data.Model.Article
import com.example.news_app_mvvm_sec20.data.util.Resource
import com.example.news_app_mvvm_sec20.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.deleteNews(article)//no return value so we write it as one line function


}