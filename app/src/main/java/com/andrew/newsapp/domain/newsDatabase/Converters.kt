package com.andrew.newsapp.domain.newsDatabase

import androidx.room.TypeConverter
import com.andrew.newsapp.entities.DbMultimedia
import com.squareup.moshi.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

private val moshi by lazy { Moshi.Builder().build() }

class MultiMediaConverters {
    private val adapter = moshi.adapter<List<DbMultimedia>>(DbMultimedia::class.java)
    @TypeConverter
    fun fromJson(json:String)=adapter.fromJson(json)
    @TypeConverter
    fun toJson( media:List<DbMultimedia>)=adapter.toJson(media)
}

class StringConverters {
    private val adapter = moshi.adapter<List<String>>(String()::class.java)
    @TypeConverter
    fun fromJson(json:String)=adapter.fromJson(json)
    @TypeConverter
    fun toJson( data:List<String>)=adapter.toJson(data)
}
