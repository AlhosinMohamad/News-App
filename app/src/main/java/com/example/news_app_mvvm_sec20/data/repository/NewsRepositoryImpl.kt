package com.example.news_app_mvvm_sec20.data.repository

import com.example.news_app_mvvm_sec20.data.Model.Api_Response
import com.example.news_app_mvvm_sec20.data.Model.Article
import com.example.news_app_mvvm_sec20.data.repository.dataSource.NewsLocalDataSource
import com.example.news_app_mvvm_sec20.data.repository.dataSource.NewsRemoteDataSource
import com.example.news_app_mvvm_sec20.data.util.Resource
import com.example.news_app_mvvm_sec20.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource : NewsLocalDataSource
): NewsRepository{

    override suspend fun getNewsHeadlines(country:String, page:Int): Resource<Api_Response> {
        return responseToResource(newsRemoteDataSource.getHotHeadLines(country, page))
    }

    private fun responseToResource(response:Response<Api_Response>): Resource<Api_Response>{
        if (response.isSuccessful){
            response.body()?.let {
                result -> return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getSearchedNews(country:String,page : Int,searchedQuery: String): Resource<Api_Response> {
        return responseToResource(newsRemoteDataSource.getSearchedHeadLines(country,page,searchedQuery))
    }

    override suspend fun saveNews(article: Article) {
       newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticleFromDB(article)
    }

    override fun getSavedNewsFromDataBase(): Flow<List<Article>> {
       return newsLocalDataSource.getSavedArticles()
    }
}