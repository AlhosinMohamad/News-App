package com.example.news_app_mvvm_sec20.data.api

import com.example.news_app_mvvm_sec20.BuildConfig
import com.example.news_app_mvvm_sec20.data.Model.Api_Response
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")//api Endpoint
    suspend fun getTopHeadLines(
        @Query("country")
        country:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = BuildConfig.API_KEY
    ):Response<Api_Response>
    
    
    ////Search Functionality: search function is the same as getTopHeadLines with one Deffirence which is the q parameter
    @GET("v2/top-headlines")//api Endpoint
    suspend fun getSearchedHeadLines(
        @Query("country")
        country:String,
        @Query("page")
        page:Int,
        //----------this parameter is important for Search Query for NewsHead lines
        @Query("q")
        searchquery:String,
        //----------
        @Query("apiKey")
        apiKey:String = BuildConfig.API_KEY
    ):Response<Api_Response>
    
    
}