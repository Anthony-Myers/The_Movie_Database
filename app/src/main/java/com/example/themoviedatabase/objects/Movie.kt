package com.example.themoviedatabase.objects

class Movie(
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val releaseDate: String,
    val overview: String,
    val genres: List<String>,
    val rating: Double,
    val reviews: List<Review>,
    val videos: List<Video>,
    val imageUrls: List<String>
)
