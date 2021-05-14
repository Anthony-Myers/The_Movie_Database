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
import com.bumptech.glide.request.target.Target
import com.example.themoviedatabase.databinding.MovieVideoItemBinding
import com.example.themoviedatabase.objects.Video
import com.example.themoviedatabase.R
import com.example.themoviedatabase.ui.main.MainViewModel

class VideoAdapter(
    private val videos: List<Video>,
    private val listener: ((video: Video)->Unit),
    private val viewModel: MainViewModel
): RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
        VideoViewHolder(
            MovieVideoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = videos.size


    override fun onBindViewHolder(holder: VideoViewHolder, position: Int): Unit =
        with(holder) {
            val video = videos[position]
            loadData(video)
            holder.itemView.setOnClickListener { listener(video) }
            binding.footerContainer.setBackgroundColor(binding.root.context.resources.getColor(viewModel.getRandomColor()))
        }

    class VideoViewHolder(val binding: MovieVideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun loadData(video: Video) = with(binding) {
            binding.footerTv.text = binding.root.context.resources.getString(R.string.title, video.title)

            Glide.with(binding.trailerIv)
                .load(video.thumbnailUrl)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                        return false
                    }
                    override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                        binding.trailerIv.visibility = ImageView.VISIBLE
                        binding.loadingImagePb.visibility = ProgressBar.INVISIBLE
                        return false
                    }
                })
                .into(binding.trailerIv)
        }
    }
}