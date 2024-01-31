package com.example.news_app_mvvm_sec20.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news_app_mvvm_sec20.data.Model.Article

@Database(
	entities = [Article::class],
	version = 2,
	exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticleDataBase: RoomDatabase() {
	
	abstract fun getArticleDao():ArticleDAO
}