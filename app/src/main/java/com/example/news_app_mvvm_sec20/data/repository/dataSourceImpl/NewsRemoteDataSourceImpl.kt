package com.example.news_app_mvvm_sec20.data.repository.dataSourceImpl

import com.example.news_app_mvvm_sec20.data.Model.Api_Response
import com.example.news_app_mvvm_sec20.data.api.NewsApiService
import com.example.news_app_mvvm_sec20.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl (
    private val newsApiService:NewsApiService,

): NewsRemoteDataSource{
    override suspend fun getHotHeadLines(country:String, page:Int): Response<Api_Response> {
        return newsApiService.getTopHeadLines(country,page)
    }
    
    override suspend fun getSearchedHeadLines(
        country : String,
        page : Int,
        searchQuery : String
    ) : Response<Api_Response> {
        return newsApiService.getSearchedHeadLines(country,page,searchQuery)
    }
    
    
}