package com.example.news_app_mvvm_sec20.presetation.di

import com.example.news_app_mvvm_sec20.data.api.NewsApiService
import com.example.news_app_mvvm_sec20.data.repository.dataSource.NewsRemoteDataSource
import com.example.news_app_mvvm_sec20.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
	
	@Provides
	@Singleton
	fun provideNewsRemoteDataSource(newsApiService : NewsApiService): NewsRemoteDataSource{
		return NewsRemoteDataSourceImpl(newsApiService)
	}
	
}