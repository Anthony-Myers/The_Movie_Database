package com.example.themoviedatabase.data.models

import com.google.gson.annotations.SerializedName

data class ReviewResultsDTO (
    @SerializedName("results")val reviewRresults: List<ReviewDTO>
        )