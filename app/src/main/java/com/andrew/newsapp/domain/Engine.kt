package com.andrew.newsapp.domain

import android.content.Context
import com.andrew.newsapp.entities.DbMultimedia
import com.andrew.newsapp.entities.DbNewsPiece
import com.andrew.newsapp.entities.Multimedia
import com.andrew.newsapp.entities.NewsPiece

fun List<NewsPiece>.toDpNews() = map { it.toDbNewsPiece() }

fun NewsPiece.toDbNewsPiece() = DbNewsPiece(
    section.toString(),
    subsection,
    title.toString(),
    abstract,
    byline,
    updateDate,
    descriptionFacet.toString(),
    geoFacet.toString(),
    shortUrl,
    multimedia?.thumbUrl()
)

fun List<Multimedia>.thumbUrl(): String? {
    forEach { if (it.height == 140 && it.width == 210) return it.url }
    return ""
}

fun List<Multimedia>.toDbMultimedia(title: String) =
    map { DbMultimedia(title, it.url ?: "", it.format) }


object AppContext {
    lateinit var context: Context private set

    fun init(context: Context) {
        this.context = context
    }
}