package com.example.decomposesample.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    val page : Long,
    @SerialName("total_pages")
    val totalPage : Long,
    @SerialName("total_results")
    val totalResults : Long,
    val results : List<Movie>
)

@Serializable
data class Movie(
    val id : Int,
    val overview : String,
    val popularity : Double,
    @SerialName("vote_count")
    val voteCount : Int,
    @SerialName("vote_average")
    val voteAverage : Double,
    @SerialName("original_language")
    val OriginalLanguage : String,
    @SerialName("backdrop_path")
    val backdropPath : String?,
    @SerialName("poster_path")
    val posterPath : String?,
    @SerialName("media_type")
    val mediaType : String,
    @SerialName("genre_ids")
    val genres: List<Int>
)