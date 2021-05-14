package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class VideoResultsDTO(
    @SerializedName("results") val movieVideoResults: List<MovieVideoDTO>
)