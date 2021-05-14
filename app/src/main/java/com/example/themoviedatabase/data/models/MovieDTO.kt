package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class MovieDTO(
    @SerializedName("id") val movieID: Int
)