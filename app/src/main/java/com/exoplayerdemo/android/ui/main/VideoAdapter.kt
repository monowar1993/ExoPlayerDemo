package com.exoplayerdemo.android.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.exoplayerdemo.android.R
import com.exoplayerdemo.android.data.repository.Video
import kotlinx.android.synthetic.main.item_video.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
class VideoAdapter(
        private val context: Context,
        private val list: MutableList<Video>,
        private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItem(@IntRange(from = 0) position: Int): Video = list[position]

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.onClick { onItemClick(adapterPosition) }
        }

        val tvName = itemView.tvName

        fun bind(item: Video) {
            tvName.text = item.name
        }
    }
}