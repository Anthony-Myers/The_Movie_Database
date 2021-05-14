package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class AuthorDTO(
    @SerializedName("username")val username: String = "",
    @SerializedName("avatar_path")val avatarUrl: String?,
    @SerializedName("rating")val rating: Double
)