package com.example.news_app_mvvm_sec20.data.Model


import com.google.gson.annotations.SerializedName

data class Api_Response(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)