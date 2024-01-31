package com.example.news_app_mvvm_sec20.presetation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.news_app_mvvm_sec20.data.Model.Api_Response
import com.example.news_app_mvvm_sec20.data.Model.Article
import com.example.news_app_mvvm_sec20.data.util.Resource
import com.example.news_app_mvvm_sec20.domain.usacase.DeleteSavedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetNewsHeadLinesUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetSavedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetSearchedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.SaveNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val application : Application,
    private val getNewsHeadLinesUseCase : GetNewsHeadLinesUseCase,
    private val getSearchedNewsUseCase : GetSearchedNewsUseCase,
    private val saveNewsUseCase : SaveNewsUseCase,
    private val getSavedNewsUseCase : GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase : DeleteSavedNewsUseCase
) : AndroidViewModel(application)
{// we need the context for checking the internet connection, so we extend from AndroidViewModel
    
    //1- get Head Lines Functionality
    val newsHeadLines: MutableLiveData<Resource<Api_Response>> =MutableLiveData()
    
    fun getNewsHeadLines(country:String,page:Int){
        viewModelScope.launch(Dispatchers.IO) {
            newsHeadLines.postValue(Resource.Loading())
            try {
                if(isNetworkAvailable(application)){
                    val apiResult=getNewsHeadLinesUseCase.execute(country,page)
                    newsHeadLines.postValue(apiResult)
                }else{
                    newsHeadLines.postValue(Resource.Error("Internet is not Available"))
                }
            }catch (e:Exception){
                newsHeadLines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }
    //-----------------
    
    //2- Search Functionality
    val searchedNews: MutableLiveData<Resource<Api_Response>> =MutableLiveData()
    
    fun getSearchedNews(country:String,page:Int,searchQuery:String){
        viewModelScope.launch (Dispatchers.IO) {
            searchedNews.postValue(Resource.Loading())//1- Loading
            try {
            	if(isNetworkAvailable(application)){//2- check Internet
                    val apiResult=getSearchedNewsUseCase.execute(country,page,searchQuery)//3- get Searched News
                    searchedNews.postValue(apiResult)//3- Add Searched News to the searchedNews List
                }else{//2- check Internet is bad
                    searchedNews.postValue(Resource.Error("Internet is not Available"))
                }
            }catch (e:Exception){
                searchedNews.postValue(Resource.Error(e.message.toString()))
            }
        }
    }
    //-----------------
    
    //3- Save Article To DB Functionality
    fun saveNewstoDB(article : Article){
        viewModelScope.launch(Dispatchers.IO) {
            saveNewsUseCase.execute(article)
        }
    }
    //-----------------
    
    //4- Get Saved Articles From DB
    fun getSavedNews()= liveData {
        getSavedNewsUseCase.execute().collect{
            emit(it)
        }
    }
    //----------------
    
    //5- Delete Article From DB
    fun deleteArticle(article : Article){
        viewModelScope.launch(Dispatchers.IO) {
            deleteSavedNewsUseCase.execute(article)
        }
    }
    
    //6- Network Connection Check
    private fun isNetworkAvailable(context: Context?): Boolean {
        var result = false
        
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return result
    }

    
}