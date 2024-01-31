package com.example.news_app_mvvm_sec20.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news_app_mvvm_sec20.data.Model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(article : Article)
	
	@Query("select * from articles")
	fun getAllArticles(): Flow<List<Article>>
	
	@Delete
	suspend fun deleteArticle(article : Article)
	
	
}