package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class MovieDetailsDTO(
    @SerializedName("title") val title: String = "",
    @SerializedName("poster_path") val posterUrl: String = "",
    @SerializedName("backdrop_path") val backdropUrl: String = "",
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("overview") val overview: String = "",
    @SerializedName("genres") val genres: List<GenreDTO>,
    @SerializedName("vote_average")val rating: Double
)