package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class ImageResultsDTO(
    @SerializedName("backdrops") val thumbnails: List<ImageDTO>
)