package com.exoplayerdemo.android.data.repository

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
data class Video(
        val name: String = "",
        val path: String = ""
) {
    override fun toString(): String {
        return "Video(name='$name', path='$path')"
    }
}