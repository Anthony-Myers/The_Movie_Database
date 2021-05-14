package com.example.themoviedatabase.data.remote.network

import com.example.themoviedatabase.data.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class MovieManager {
    private val service: MovieService
    private val retrofit = RetrofitService.providesRetrofitService()

    init{
        service = retrofit.create(MovieService::class.java)
    }

    suspend fun getTrendingMovies(apiKey: String) = service.getTrendingMovies(apiKey)

    suspend fun getMovieDetails(movieId: Int, apiKey: String) = service.getMovieDetails(movieId, apiKey)

    suspend fun getMovieReviews(movieId: Int, apiKey: String) = service.getMovieReviews(movieId, apiKey)

    suspend fun getMovieVideos(movieId: Int, apiKey: String) = service.getMovieVideos(movieId, apiKey)

    suspend fun getMovieImages(movieId: Int, apiKey: String) = service.getMovieImages(movieId, apiKey)

    interface MovieService{
        @GET("/3/trending/movie/week")
        suspend fun getTrendingMovies(
            @Query("api_key") key: String
        ): ResultDTO

        @GET("/3/movie/{id}")
        suspend fun getMovieDetails(
            @Path("id") movieId: Int,
            @Query("api_key") key: String
        ): MovieDetailsDTO

        @GET("/3/movie/{id}/reviews")
        suspend fun getMovieReviews(
            @Path("id") movieId: Int,
            @Query("api_key") key: String
        ): ReviewResultsDTO

        @GET("/3/movie/{id}/videos")
        suspend fun getMovieVideos(
            @Path("id") movieId: Int,
            @Query("api_key") key: String
        ): VideoResultsDTO

        @GET("/3/movie/{id}/images")
        suspend fun getMovieImages(
            @Path("id") movieId: Int,
            @Query("api_key") key: String
        ): ImageResultsDTO
    }
}