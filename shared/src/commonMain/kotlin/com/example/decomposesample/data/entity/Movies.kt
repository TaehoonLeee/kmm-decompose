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
    val id : Long?,
    val video : Boolean?,
    @SerialName("vote_count")
    val voteCount : Long?,
    @SerialName("vote_average")
    val voteAverage : Double?,
    val title : String?,
    @SerialName("release_date")
    val releaseDate : String?,
    @SerialName("original_language")
    val OriginalLanguage : String?,
    @SerialName("backdrop_path")
    val backdropPath : String?,
    val adult : Boolean?,
    @SerialName("overview")
    val overview : String?,
    @SerialName("poster_path")
    val posterPath : String?,
    @SerialName("popularity")
    val popularity : Double?,
    @SerialName("media_type")
    val mediaType : String?,
    val genres: List<Genre>?,
    val runtime : Long?,
    val credits : Credit?
)

@Serializable
data class Genre(
    val id : Long,
    val name : String
)