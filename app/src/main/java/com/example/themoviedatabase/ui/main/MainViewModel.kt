package com.example.themoviedatabase.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.themoviedatabase.R
import com.example.themoviedatabase.data.repos.MovieRepository
import com.example.themoviedatabase.objects.Movie
import com.example.themoviedatabase.objects.Review
import com.example.themoviedatabase.objects.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList
import kotlin.random.Random

class MainViewModel: ViewModel() {
    private val _repo: MovieRepository by lazy {
        MovieRepository()
    }

    private var _movieCollection: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    val movieCollection: LiveData<List<Movie>> get() = _movieCollection

    private var _loadingState: MutableLiveData<String> = MutableLiveData<String>()
    val loadingState: LiveData<String> get() = _loadingState

    private var _curMovie: MutableLiveData<Movie> = MutableLiveData<Movie>()
    val curMovie: LiveData<Movie> get() = _curMovie

    private val colorCollection: ArrayList<Int> = ArrayList()

    init {
        getTrendingMovies()
        createColorCollection()
    }

    private fun getTrendingMovies() = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) { _loadingState.value = "Loading..." }
        try {
            _movieCollection.postValue(_repo.getTrendingMovies().results.map { it ->
                val movieDetails = _repo.getMovieDetails(it.movieID)
                val movieReviews = _repo.getMovieReviews(it.movieID)
                val movieVideos = _repo.getMovieVideos(it.movieID)
                val movieImages = _repo.getMovieImages(it.movieID)

                val genreList = movieDetails.genres.map { it.genreName }.toList()
                val reviewList =
                    movieReviews.reviewRresults.map {
                        Review(
                            it.author.username,
                            it.content,
                            createAvatarUrl(it.author.avatarUrl),
                            it.author.rating
                        )
                    }.toList()
                val videoList = movieVideos.movieVideoResults.map {
                    Video(
                        createVideoUrl(it.videoUrlKey),
                        it.videoName
                    )
                }.toList()
                val imageList =
                    movieImages.thumbnails.map { createMovieImageUrl(it.imageUrl) }.toList()

                var imageIndex = 1

                videoList.forEach{
                    if(imageIndex==imageList.size)imageIndex = 0
                    it.thumbnailUrl = imageList[imageIndex]
                    imageIndex++
                }

                Movie(
                    movieDetails.title,
                    createMovieImageUrl(movieDetails.posterUrl),
                    createMovieImageUrl(movieDetails.backdropUrl),
                    movieDetails.releaseDate,
                    movieDetails.overview,
                    genreList,
                    movieDetails.rating,
                    reviewList,
                    videoList,
                    imageList
                )
            }.toList())
        } catch (e: Exception) {
            _loadingState.postValue("An ERROR has occurred.")
            Log.d(MainViewModel::class.java.simpleName, e.message.toString())
        }

    }

    private fun createAvatarUrl(path: String?): String? {
        if(path==null){
            return path
        }
        if (path.contains(".com")) {
            return path.substring(1, path.length-1)
        }
        return "https://www.themoviedb.org/t/p/w300_and_h300_face/$path"
    }

    private fun createMovieImageUrl(path: String): String {
        return "https://image.tmdb.org/t/p/w500$path"
    }

    private fun createVideoUrl(path: String): String {
        return "https://www.themoviedb.org/video/play?key=$path"
    }

    private fun createColorCollection() {
        colorCollection.add(R.color.transparent_blue)
        colorCollection.add(R.color.transparent_green)
        colorCollection.add(R.color.transparent_grey)
        colorCollection.add(R.color.transparent_purple)
        colorCollection.add(R.color.transparent_red)
        colorCollection.add(R.color.transparent_yellow)
    }

    fun getRandomColor(): Int {
        return colorCollection[Random.nextInt(0, colorCollection.size - 1)]
    }

    fun setCurrentMovie(movie: Movie){
        _curMovie.value = movie
    }
}