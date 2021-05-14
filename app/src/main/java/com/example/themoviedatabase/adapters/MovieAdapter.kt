package com.example.themoviedatabase.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.themoviedatabase.R
import com.example.themoviedatabase.databinding.MovieItemBinding
import com.example.themoviedatabase.objects.Movie
import com.example.themoviedatabase.ui.main.MainViewModel
import com.bumptech.glide.request.target.Target

class MovieAdapter(
    private val movies: List<Movie>,
    private val listener: ((movie: Movie)->Unit),
    private val viewModel: MainViewModel
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = movies.size


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int): Unit =
        with(holder) {
            val movie = movies[position]
            loadData(movie)
            itemView.setOnClickListener { listener(movie) }
            binding.footerContainer.setBackgroundColor(binding.root.context.resources.getColor(viewModel.getRandomColor()))
        }

    class MovieViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun loadData(movie: Movie) = with(binding) {
            binding.footerTv.text = binding.root.context.resources.getString(R.string.title, movie.title)

            Glide.with(binding.posterIv)
                .load(movie.posterUrl)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                        return false
                    }
                    override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                        binding.posterIv.visibility = ImageView.VISIBLE
                        binding.loadingImagePb.visibility = ProgressBar.INVISIBLE
                        return false
                    }
                })
                .into(binding.posterIv)
        }
    }
}