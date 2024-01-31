package com.example.news_app_mvvm_sec20.presetation.di

import android.app.Application
import androidx.room.Room
import com.example.news_app_mvvm_sec20.data.db.ArticleDAO
import com.example.news_app_mvvm_sec20.data.db.ArticleDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
	
	@Singleton
	@Provides
	fun provideNewsDatabase(application : Application):ArticleDataBase{
		return Room.databaseBuilder(application,ArticleDataBase::class.java,"news_db")
			.fallbackToDestructiveMigration()
			.build()
	}
	
	@Singleton
	@Provides
	fun provideNewsDao(articleDataBase : ArticleDataBase ):ArticleDAO{
		return articleDataBase.getArticleDao()
	}
	
	
}