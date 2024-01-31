package com.example.news_app_mvvm_sec20.data.db

import androidx.room.TypeConverter
import com.example.news_app_mvvm_sec20.data.Model.Source

class Converters {
	
	//because we want to make Source parameter in Article Class as nullable but it is a reference of a class so we have tow solutions:
	//1- make Source class as Entity of the database which is an efficient solution because we need just the name of it
	//2- we make this function to get just the name of the source with @TypeConverter Annotation.
	@TypeConverter
	fun nameFromSource(source : Source):String{
		return source.name
	}
	
	//3- when we save an article we should save the source of it but we just use the name of it so, we make this function
	@TypeConverter
	fun nameToSource(name : String):Source{
		return Source(name,name)
	}
}