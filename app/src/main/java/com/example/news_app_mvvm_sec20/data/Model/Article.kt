package com.example.news_app_mvvm_sec20.data.Model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "articles") //Read News from database: Room table
data class Article(
    //----------Room DB
    @PrimaryKey(autoGenerate = true)
    val id:Int?=0,
    
    //----------Model
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
): Serializable {
    
    companion object {
        private const val serialVersionUID: Long = 1L
    }
    
    override fun hashCode(): Int {
        var result = author.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + publishedAt.hashCode()
        result = 31 * result + source.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + urlToImage.hashCode()
        return result
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as Article
        
        if (author != other.author) return false
        if (content != other.content) return false
        if (description != other.description) return false
        if (publishedAt != other.publishedAt) return false
        if (source != other.source) return false
        if (title != other.title) return false
        if (url != other.url) return false
        if (urlToImage != other.urlToImage) return false
        
        return true
    }
}//  2- Web View clickable list item Functionality,
           // make article Serializable in order to pass it as a parameter for the web view to show the details