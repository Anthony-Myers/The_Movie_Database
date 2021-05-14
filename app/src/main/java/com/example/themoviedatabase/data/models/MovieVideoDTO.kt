package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class MovieVideoDTO (
    @SerializedName("key")val videoUrlKey: String = "",
    @SerializedName("name")val videoName: String = ""
)