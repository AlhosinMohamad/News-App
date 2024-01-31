package com.example.news_app_mvvm_sec20.domain.usacase

import com.example.news_app_mvvm_sec20.data.Model.Api_Response
import com.example.news_app_mvvm_sec20.data.util.Resource
import com.example.news_app_mvvm_sec20.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country:String,page:Int,searchedQuery:String): Resource<Api_Response> {
        return newsRepository.getSearchedNews(country,page,searchedQuery)
    }

}