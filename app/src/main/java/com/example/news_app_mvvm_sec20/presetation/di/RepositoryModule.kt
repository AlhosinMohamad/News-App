package com.example.news_app_mvvm_sec20.presetation.di

import com.example.news_app_mvvm_sec20.data.repository.NewsRepositoryImpl
import com.example.news_app_mvvm_sec20.data.repository.dataSource.NewsLocalDataSource
import com.example.news_app_mvvm_sec20.data.repository.dataSource.NewsRemoteDataSource
import com.example.news_app_mvvm_sec20.domain.repository.NewsRepository
import com.example.news_app_mvvm_sec20.domain.usacase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
	
	@Provides
	@Singleton
	fun provideRepository(
		newsRemoteDataSource : NewsRemoteDataSource,
		newsLocalDataSource : NewsLocalDataSource
	):NewsRepository{
		return NewsRepositoryImpl(newsRemoteDataSource,newsLocalDataSource)
	}
}