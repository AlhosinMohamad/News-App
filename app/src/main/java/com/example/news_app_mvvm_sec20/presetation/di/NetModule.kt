package com.example.news_app_mvvm_sec20.presetation.di

import com.example.news_app_mvvm_sec20.BuildConfig
import com.example.news_app_mvvm_sec20.data.api.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
	
	@Singleton
	@Provides
	fun provideRetrofit():Retrofit{
		return Retrofit.Builder()
			.baseUrl(BuildConfig.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			
	}
	
	@Singleton
	@Provides
	fun provideNewsApiService(retrofit : Retrofit):NewsApiService{
		return retrofit.create(NewsApiService::class.java)
	}
}