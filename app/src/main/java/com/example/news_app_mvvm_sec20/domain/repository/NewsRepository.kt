package com.example.news_app_mvvm_sec20.domain.repository

import com.example.news_app_mvvm_sec20.data.Model.Api_Response
import com.example.news_app_mvvm_sec20.data.Model.Article
import com.example.news_app_mvvm_sec20.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines(country:String, page:Int):Resource<Api_Response>//for getNewsHeadlines Use Case

    suspend fun getSearchedNews(country:String,page:Int,searchedQuery: String): Resource<Api_Response>//for getSearchedNews Use Case

    suspend fun saveNews(article: Article)//for saveNews Use Case

    suspend fun deleteNews(article: Article)// for deleteNews Use Case

    fun getSavedNewsFromDataBase(): Flow<List<Article>>//for getSavedNewsFromDataBase Use Case

    //1- it isnt a suspend function because we dont want to pause and resume it in later time
    //2-Room database give us the data as a stream of data so we use Flow because
    //it is better than liveData to handle stream of Data and also we cant use live data in a repository
   // due to mvvm Restrictions because it will cause a lot of errors and exceptions
}