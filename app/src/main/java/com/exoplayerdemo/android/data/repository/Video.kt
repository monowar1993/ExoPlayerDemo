package com.exoplayerdemo.android.data.repository

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
data class Video(
        val id: Int = 0,
        val name: String = "",
        val path: String = "",
        val thumbnail: String = ""
) {
    override fun toString(): String {
        return "Video(id='$id', name='$name', path='$path', thumbnail='$thumbnail')"
    }
}