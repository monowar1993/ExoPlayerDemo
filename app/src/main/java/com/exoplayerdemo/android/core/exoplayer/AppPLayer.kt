package com.exoplayerdemo.android.core.exoplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.SimpleExoPlayer

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
interface AppPLayer {
    fun initializePlayer(context: Context): SimpleExoPlayer
    fun play(context: Context, uri: Uri, playWhenReady: Boolean = true, playbackPosition: Long = 0)
    fun playPause(play: Boolean)
    fun forward()
    fun rewind()
    fun releasePlayer()
}