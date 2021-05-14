package com.example.themoviedatabase.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.themoviedatabase.R
import com.example.themoviedatabase.adapters.ReviewAdapter
import com.example.themoviedatabase.adapters.VideoAdapter
import com.example.themoviedatabase.databinding.MovieDetailsFragmentBinding
import com.example.themoviedatabase.objects.Video


class MovieDetailsFragment : Fragment() {
    private var _binding: MovieDetailsFragmentBinding? = null
    private val binding: MovieDetailsFragmentBinding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailsFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        bindMovieDetails()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun bindMovieDetails() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.backB?.setOnClickListener { findNavController().navigate(R.id.action_pop_to_list) }
        }
        binding.bannerIv?.let {
            Glide.with(it)
                .load(viewModel.curMovie.value?.backdropUrl)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                        return false
                    }
                    override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                        binding.bannerIv.visibility = ImageView.VISIBLE
                        binding.loadingImagePb.visibility = ProgressBar.INVISIBLE
                        return false
                    }
                })
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
        binding.reviewsRv?.adapter = viewModel.curMovie.value?.reviews?.let { ReviewAdapter(it) }
        binding.summaryContentTv?.text = context?.resources?.getString(
            R.string.body,
            viewModel.curMovie.value?.overview
        )
        binding.trailersRv?.adapter =
            viewModel.curMovie.value?.videos?.let {
                VideoAdapter(
                    it,
                    this@MovieDetailsFragment::onVideoClick,
                    viewModel
                )
            }
        binding.reviewsRv?.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        if(viewModel.curMovie.value?.reviews?.isEmpty() == true){
            binding.reviewsRv?.layoutParams?.height = 0
            binding.reviewsContainer?.visibility = LinearLayout.INVISIBLE
            binding.noReviewsTv?.visibility = TextView.VISIBLE
        }
    }

    private fun onVideoClick(video: Video) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
        startActivity(browserIntent)
    }
}