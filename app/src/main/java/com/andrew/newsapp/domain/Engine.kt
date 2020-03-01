package com.andrew.newsapp.domain

import com.andrew.newsapp.entities.DbMultimedia
import com.andrew.newsapp.entities.DpNewsPiece
import com.andrew.newsapp.entities.Multimedia
import com.andrew.newsapp.entities.NewsPiece

fun List<NewsPiece>.toDpNews()=map { it.toDbNewsPiece() }

fun NewsPiece.toDbNewsPiece() = DpNewsPiece(
    section,
    subsection,
    title,
    abstract,
    byline,
    updateDate,
    descriptionFacet,
    geoFacet,
    multimedia?.toDbMultimedia(),
    shortUrl
)

fun List<Multimedia>.toDbMultimedia() =
    map { DbMultimedia(it.url, it.format) }