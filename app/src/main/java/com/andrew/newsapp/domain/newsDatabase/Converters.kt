package com.andrew.newsapp.domain.newsDatabase

import androidx.room.TypeConverter
import com.andrew.newsapp.entities.DbMultimedia
import com.squareup.moshi.*

private val moshi by lazy { Moshi.Builder().build() }

class MultiMediaConverters {
    private val adapter = moshi.adapter<List<DbMultimedia>>(DbMultimedia::class.java)
    @TypeConverter
    fun fromJson(json:String)=adapter.fromJson(json)
    @TypeConverter
    fun toJson( media:List<DbMultimedia>)=media.toString()
}

class StringConverters {
    private val adapter = moshi.adapter<List<String>>(String()::class.java)
    @TypeConverter
    @FromJson
    fun fromJson(json:String)=adapter.fromJson(json)
    @TypeConverter
    @ToJson
    fun toJson( data:List<String>)=data.toString()
}
