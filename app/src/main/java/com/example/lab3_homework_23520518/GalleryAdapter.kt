package com.example.lab3_homework_23520518

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.lab3_homework_23520518.R
import com.example.lab3_homework_23520518.model.ProcessedImage

class GalleryAdapter : PagingDataAdapter<ProcessedImage, GalleryAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val tvAiTags: TextView = itemView.findViewById(R.id.tvAiTags)
        private val tvOriginalTags: TextView = itemView.findViewById(R.id.tvOriginalTags)

        fun bind(processedImage: ProcessedImage) {
            // Load image using Coil
            imageView.load(processedImage.imageItem.imageUrl) {
                crossfade(true)
            }

            // Display ML Kit Tags
            tvAiTags.text = "AI Detected: ${processedImage.aiTags.joinToString(", ")}"

            // Display Original Pixabay Tags
            tvOriginalTags.text = "Pixabay Tags: ${processedImage.imageItem.tags}"
        }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<ProcessedImage>() {
            override fun areItemsTheSame(oldItem: ProcessedImage, newItem: ProcessedImage): Boolean {
                return oldItem.imageItem.id == newItem.imageItem.id
            }

            override fun areContentsTheSame(oldItem: ProcessedImage, newItem: ProcessedImage): Boolean {
                return oldItem == newItem
            }
        }
    }
}