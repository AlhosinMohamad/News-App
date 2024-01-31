package com.example.news_app_mvvm_sec20.data.repository.dataSource

import com.example.news_app_mvvm_sec20.data.Model.Api_Response
import com.example.news_app_mvvm_sec20.data.Model.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRemoteDataSource {

    suspend fun getHotHeadLines(country:String, page:Int):Response<Api_Response>
    
    //Search Functionality
    suspend fun getSearchedHeadLines(country:String, page:Int,searchQuery:String):Response<Api_Response>
    
    

}