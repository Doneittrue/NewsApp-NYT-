package com.andrew.newsapp.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NewsResponse(
    val status: String? = "",
    val copyright: String = "",
    val section: String = "",
    val last_updated: String = "",
    @Json(name = "num_results")
    val resultCount: Int = 0,
    @Json(name = "results")
    val news: List<NewsPiece> = listOf()
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NewsPiece(
    val section: String? = "",
    val subsection: String? = "",
    val title: String? = "",
    val abstract: String? = "",
    val url: String? = "",
    val uri: String? = "",
    val byline: String? = "",
    @Json(name = "item_type")
    val itemType: String? = "",
    @Json(name = "updated_date")
    val updateDate: String? = "",
    @Json(name = "created_date")
    val creationDate: String? = "",
    @Json(name = "published_date")
    val publishDate: String? = "",
    @Json(name = "material_type_facet")
    val materialTypeFacet: String? = "",
    val kicker: String? = "",
    @Json(name = "des_facet")
    val descriptionFacet: List<String>? = listOf(),
    @Json(name = "org_facet")
    val organizationFacet: List<String>? = listOf(),
    @Json(name = "per_facet")
    val perFacet: List<String>? = listOf(),
    @Json(name = "geo_facet")
    val geoFacet: List<String>? = listOf(),
    val multimedia: List<Multimedia>? = listOf(),
    @Json(name = "short_url")
    val shortUrl: String? = ""
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Multimedia(
    val url: String? = "",
    val format: String? = "",
    val height: Int? = 0,
    val width: Int? = 0,
    val type: String? = "",
    val subtype: String? = "",
    val caption: String? = "",
    val copyright: String? = ""
) : Parcelable

@Parcelize
data class DpNewsPiece(
    val section: String? = "",
    val subsection: String? = "",
    val title: String? = "",
    val abstract: String? = "",
    val byline: String? = "",
    @Json(name = "updated_date")
    val updateDate: String? = "",
    @Json(name = "des_facet")
    val descriptionFacet: List<String>? = listOf(),
    @Json(name = "geo_facet")
    val geoFacet: List<String>? = listOf(),
    val multimedia: List<DbMultimedia>? = listOf(),
    @Json(name = "short_url")
    val shortUrl: String? = "",
    var isFavourite: Boolean = false
) : Parcelable

@Parcelize
data class DbMultimedia(
    val url: String? = "",
    val format: String? = ""
) : Parcelable
