package com.example.news_app_mvvm_sec20.presetation.di

import com.example.news_app_mvvm_sec20.presetation.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsAdapterModule {
	
	@Singleton
	@Provides
	fun provideNewsAdapterModule(): NewsAdapter {
		return NewsAdapter()
	}
}