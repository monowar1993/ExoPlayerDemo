package com.exoplayerdemo.android.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.exoplayerdemo.android.R
import com.exoplayerdemo.android.core.GlideApp
import com.exoplayerdemo.android.data.repository.Video
import kotlinx.android.synthetic.main.item_video.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.io.File

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
class VideoAdapter(
    private val context: Context,
    private val list: MutableList<Video>
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private lateinit var onItemClick: (Int) -> Unit

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

    fun setOnItemClickListener(onItemClick: (Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName = itemView.tvName
        val ivThumbnail = itemView.ivThumbnail

        init {
            itemView.onClick { onItemClick(adapterPosition) }
        }

        fun bind(item: Video) {
            GlideApp.with(context)
                .load(File(item.thumbnail))
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .into(ivThumbnail)

            tvName.text = item.name
        }
    }
}