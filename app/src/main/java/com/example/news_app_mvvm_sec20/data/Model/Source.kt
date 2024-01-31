package com.example.news_app_mvvm_sec20.data.Model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Source(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
): Serializable {
    
    companion object {
        private const val serialVersionUID: Long = 1L
    }
    
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as Source
        
        if (id != other.id) return false
        if (name != other.name) return false
        
        return true
    }
}