package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class ImageDTO(
    @SerializedName("file_path") val imageUrl: String = ""
)