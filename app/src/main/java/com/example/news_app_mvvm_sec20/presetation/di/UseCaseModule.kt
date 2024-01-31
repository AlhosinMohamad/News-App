package com.example.news_app_mvvm_sec20.presetation.di

import com.example.news_app_mvvm_sec20.domain.repository.NewsRepository
import com.example.news_app_mvvm_sec20.domain.usacase.DeleteSavedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetNewsHeadLinesUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetSavedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetSearchedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
	
	@Provides
	@Singleton
	fun provideGetNewsHeadLinesUseCase(
		newsRepository : NewsRepository
	):GetNewsHeadLinesUseCase{
		return GetNewsHeadLinesUseCase(newsRepository)
	}
	
	@Provides
	@Singleton
	fun provideGetSearchedNewsUseCase(
		newsRepository : NewsRepository
	):GetSearchedNewsUseCase{
		return GetSearchedNewsUseCase(newsRepository)
	}
	
	@Provides
	@Singleton
	fun provideSaveNewsUseCase(
		newsRepository : NewsRepository
	):SaveNewsUseCase{
		return SaveNewsUseCase(newsRepository)
	}
	
	@Provides
	@Singleton
	fun provideGetSavedNewsUseCase(
		newsRepository : NewsRepository
	):GetSavedNewsUseCase{
		return GetSavedNewsUseCase(newsRepository)
	}
	
	@Provides
	@Singleton
	fun provideDeleteSavedNewsUseCase(
		newsRepository : NewsRepository
	):DeleteSavedNewsUseCase{
		return DeleteSavedNewsUseCase(newsRepository)
	}
}