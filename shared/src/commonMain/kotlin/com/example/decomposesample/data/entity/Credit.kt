package com.example.decomposesample.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credit (
    val cast : List<Cast>
)

@Serializable
data class Cast(
    val id : Long,
    @SerialName("cast_id")
    val castId : Long?,
    @SerialName("credit_id")
    val creditId : String?,
    val character : String?,
    val gender : Int?,
    val name : String,
    @SerialName("profile_path")
    val profilePath : String?,
    @SerialName("place_of_birth")
    val placeOfBirth : String?,
    val biography : String?,
    @SerialName("known_for_department")
    val department : String?,
    @SerialName("also_known_as")
    val knownAs : List<String>?
)

@Serializable
data class Profile(
    val profiles : List<ProfileImage>
)

@Serializable
data class ProfileImage(
    val width : Int?,
    val height : Int?,
    @SerialName("file_path")
    val path : String?,
    @SerialName("aspect_ratio")
    val aspectRatio : Double?
)