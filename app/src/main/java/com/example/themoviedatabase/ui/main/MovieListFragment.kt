package com.example.themoviedatabase.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedatabase.R
import com.example.themoviedatabase.adapters.MovieAdapter
import com.example.themoviedatabase.adapters.ReviewAdapter
import com.example.themoviedatabase.adapters.VideoAdapter
import com.example.themoviedatabase.databinding.MovieListFragmentBinding
import com.example.themoviedatabase.objects.Movie
import com.example.themoviedatabase.objects.Video

class MovieListFragment : Fragment() {
    private var _binding: MovieListFragmentBinding? = null
    private val binding: MovieListFragmentBinding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.loadingIv?.let {
            Glide.with(it)
                .asGif()
                .load("https://thumbs.gfycat.com/PinkMeagerLabradorretriever-max-1mb.gif")
                .into(binding.loadingIv!!)
        }

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            if(viewModel.loadingState.value?.equals("An ERROR has occurred.") == true){
                displayError()
                return@Observer
            }
        })

        viewModel.movieCollection.observe(viewLifecycleOwner, {
            bindResults()
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        bindResults()
    }

    private fun bindResults(){
        if(viewModel.curMovie.value==null){
            viewModel.movieCollection.value?.get(0)?.let { viewModel.setCurrentMovie(it) }
        }

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            bindMovieDetails()
        }

        binding.loadingContainer?.visibility = LinearLayout.INVISIBLE
        binding.errorContainer?.visibility = LinearLayout.INVISIBLE
        binding.moviesRv?.visibility = RecyclerView.VISIBLE

        binding.moviesRv?.adapter =
            viewModel.movieCollection.value?.let { MovieAdapter(it, this@MovieListFragment::onMovieClick, viewModel) }

    }

    private fun bindMovieDetails(){
        binding.bannerIv?.let {
            Glide.with(it)
                .load(viewModel.curMovie.value?.backdropUrl)
                .into(binding.bannerIv!!)
        }
        binding.ratingTv?.text = context?.resources?.getString(
            R.string.releasedate_rating,
            viewModel.curMovie.value?.releaseDate,
            viewModel.curMovie.value?.rating.toString()
        )
        binding.titleTv?.text = context?.resources?.getString(
            R.string.title,
            viewModel.curMovie.value?.title
        )
        binding.summaryContentTv?.text = context?.resources?.getString(
            R.string.body,
            viewModel.curMovie.value?.overview
        )
        binding.trailersRv?.adapter =
            viewModel.curMovie.value?.videos?.let { VideoAdapter(
                it,
                this@MovieListFragment::onVideoClick,
                viewModel
            ) }
        binding.reviewsRv?.adapter = viewModel.curMovie.value?.reviews?.let { ReviewAdapter(it) }
        binding.reviewsRv?.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        if(viewModel.curMovie.value?.reviews?.isEmpty() == true){
            binding.reviewsRv?.layoutParams?.height = 0
            binding.reviewContainer?.visibility = LinearLayout.INVISIBLE
            binding.noReviewsTv?.visibility = TextView.VISIBLE
        }
    }

    private fun displayError(){
        binding.loadingContainer?.visibility = LinearLayout.INVISIBLE
        binding.errorContainer?.visibility = LinearLayout.VISIBLE

        binding.errorIv?.let {
            Glide.with(it)
                .asGif()
                .load("https://media2.giphy.com/media/h3oDE2n2F7DXPLzOrQ/source.gif")
                .into(binding.errorIv!!)
        }
    }

    private fun onMovieClick(movie: Movie){
        viewModel.setCurrentMovie(movie)
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            bindMovieDetails()
            return
        }
        findNavController().navigate(R.id.destination_movie_details_fragment)
    }

    private fun onVideoClick(video: Video){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
        startActivity(browserIntent)
    }
}