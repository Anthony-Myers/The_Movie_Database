package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class ResultDTO(
    @SerializedName("results") val results: List<MovieDTO>
)