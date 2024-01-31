package com.example.news_app_mvvm_sec20.domain.usacase

import com.example.news_app_mvvm_sec20.data.Model.Api_Response
import com.example.news_app_mvvm_sec20.data.Model.Article
import com.example.news_app_mvvm_sec20.data.util.Resource
import com.example.news_app_mvvm_sec20.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNewsFromDataBase()//it returns a flow of list of articles
    }
    
    
}