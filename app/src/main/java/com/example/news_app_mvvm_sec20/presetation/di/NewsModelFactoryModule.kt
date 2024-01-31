package com.example.news_app_mvvm_sec20.presetation.di

import android.app.Application
import com.example.news_app_mvvm_sec20.domain.usacase.DeleteSavedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetNewsHeadLinesUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetSavedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetSearchedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.SaveNewsUseCase
import com.example.news_app_mvvm_sec20.presetation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsModelFactoryModule {
	
	@Provides
	@Singleton
	fun provideNewsViewModelFactory(
		application : Application,
		getNewsHeadLinesUseCase : GetNewsHeadLinesUseCase,
		getSearchedNewsUseCase : GetSearchedNewsUseCase,
		saveNewsUseCase : SaveNewsUseCase,
		getSavedNewsUseCase : GetSavedNewsUseCase,
		deleteSavedNewsUseCase : DeleteSavedNewsUseCase
	): NewsViewModelFactory{
		return NewsViewModelFactory(
			application,
			getNewsHeadLinesUseCase,
			getSearchedNewsUseCase,
			saveNewsUseCase,
			getSavedNewsUseCase,
			deleteSavedNewsUseCase
		)
	}
}