package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class ReviewDTO (
    @SerializedName("author_details")val author: AuthorDTO,
    @SerializedName("content")val content: String = ""
        )