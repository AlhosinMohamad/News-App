package com.example.news_app_mvvm_sec20.presetation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news_app_mvvm_sec20.domain.usacase.DeleteSavedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetNewsHeadLinesUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetSavedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.GetSearchedNewsUseCase
import com.example.news_app_mvvm_sec20.domain.usacase.SaveNewsUseCase

class NewsViewModelFactory (
	private val application : Application,
	private val getNewsHeadLinesUseCase : GetNewsHeadLinesUseCase,
	private val getSearchedNewsUseCase : GetSearchedNewsUseCase,
	private val saveNewsUseCase : SaveNewsUseCase,
	private val getSavedNewsUseCase : GetSavedNewsUseCase,
	private val deleteSavedNewsUseCase : DeleteSavedNewsUseCase
): ViewModelProvider.Factory{
	override fun <T : ViewModel> create(modelClass : Class<T>) : T {
		return NewsViewModel(
			application,
			getNewsHeadLinesUseCase,
			getSearchedNewsUseCase,
			saveNewsUseCase,
			getSavedNewsUseCase,
			deleteSavedNewsUseCase
		) as T
	}
}