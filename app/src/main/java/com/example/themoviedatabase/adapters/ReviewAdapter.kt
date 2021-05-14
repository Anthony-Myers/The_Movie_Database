package com.example.themoviedatabase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedatabase.R
import com.example.themoviedatabase.databinding.ReviewItemBinding
import com.example.themoviedatabase.objects.Review

class ReviewAdapter(
    private val reviews: List<Review>
): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ReviewViewHolder =
        ReviewViewHolder(
            ReviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = reviews.size


    override fun onBindViewHolder(holder: ReviewAdapter.ReviewViewHolder, position: Int): Unit =
        with(holder) {
            val review = reviews[position]
            loadData(review)
        }

    class ReviewViewHolder(private val binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun loadData(review: Review) = with(binding) {
            binding.usernameRatingTv.text = binding.root.context.resources.getString(R.string.username_rating, review.author, review.rating.toString())

            binding.reviewContentTv.text = binding.root.context.resources.getString(R.string.body, review.content)

            val avatarImage = review.avatarUrl
                ?: "https://rlv.zcache.com/gold_movie_camera_symbol_classic_round_sticker-rf4e3ce5d6a4e4146b8c81aeddb80b961_0ugmp_8byvr_630.jpg?view_padding=%5B285%2C0%2C285%2C0%5D"

            Glide.with(binding.avatarIv)
                .load(avatarImage)
                .into(binding.avatarIv)
        }
    }

}