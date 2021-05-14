package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("name") val genreName: String = ""
)