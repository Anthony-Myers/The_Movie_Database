package com.example.themoviedatabase.data.repos

import com.example.themoviedatabase.BuildConfig
import com.example.themoviedatabase.data.models.*
import com.example.themoviedatabase.data.remote.network.MovieManager

class MovieRepository {
    private val movieManager: MovieManager by lazy {
        MovieManager()
    }

    suspend fun getTrendingMovies(): ResultDTO =
        movieManager.getTrendingMovies(BuildConfig.TMDB_APIKEY)

    suspend fun getMovieDetails(movieID: Int): MovieDetailsDTO =
        movieManager.getMovieDetails(movieID, BuildConfig.TMDB_APIKEY)

    suspend fun getMovieReviews(movieID: Int): ReviewResultsDTO =
        movieManager.getMovieReviews(movieID, BuildConfig.TMDB_APIKEY)

    suspend fun getMovieVideos(movieID: Int): VideoResultsDTO =
        movieManager.getMovieVideos(movieID, BuildConfig.TMDB_APIKEY)

    suspend fun getMovieImages(movieID: Int): ImageResultsDTO =
        movieManager.getMovieImages(movieID, BuildConfig.TMDB_APIKEY)
}