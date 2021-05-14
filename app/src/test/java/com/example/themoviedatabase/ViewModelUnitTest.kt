package com.example.themoviedatabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.themoviedatabase.objects.Movie
import com.example.themoviedatabase.objects.Review
import com.example.themoviedatabase.objects.Video
import com.example.themoviedatabase.ui.main.MainViewModel
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelUnitTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        mainViewModel = MainViewModel()
    }


    @Test
    fun apiCall_isSuccessful(){
        val isApiCallPopulated = mainViewModel.movieCollection.value?.isNotEmpty()
        assertThat(isApiCallPopulated, `is` (true))
    }

    @Test
    fun setCurrentMovie_isSuccessful(){
        val curMovie = Movie("test_title", "test_posterUrl", "test_backdropUrl",
            "test_releaseDate", "test_overview", ArrayList<String>(),
            0.0, ArrayList<Review>(), ArrayList<Video>(), ArrayList<String>())

        mainViewModel.setCurrentMovie(curMovie)

        assertThat(mainViewModel.curMovie.value, `is`(curMovie))
        assertThat(mainViewModel.curMovie.value?.title, `is` (curMovie.title))
        assertThat(mainViewModel.curMovie.value?.posterUrl, `is` (curMovie.posterUrl))
        assertThat(mainViewModel.curMovie.value?.backdropUrl, `is` (curMovie.backdropUrl))
        assertThat(mainViewModel.curMovie.value?.releaseDate, `is` (curMovie.releaseDate))
        assertThat(mainViewModel.curMovie.value?.overview, `is` (curMovie.overview))
        assertThat(mainViewModel.curMovie.value?.rating, `is` (curMovie.rating))
    }
}