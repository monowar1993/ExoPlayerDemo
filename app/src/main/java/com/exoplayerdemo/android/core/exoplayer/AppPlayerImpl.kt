package com.exoplayerdemo.android.core.exoplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
class AppPlayerImpl : AppPLayer {

    private var player: SimpleExoPlayer? = null

    override fun initializePlayer(context: Context): SimpleExoPlayer {

        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                context,
                DefaultRenderersFactory(context),
                DefaultTrackSelector(),
                DefaultLoadControl()
            )
        }
        return player!!
    }

    override fun play(context: Context, uri: Uri, playWhenReady: Boolean, playbackPosition: Long) {
        player?.prepare(buildMediaSource(context, uri), true, false)
        player?.playWhenReady = playWhenReady
        player?.seekTo(playbackPosition)
    }

    override fun playPause(play: Boolean) {
        player?.playWhenReady = play
    }

    override fun forward() {
        player?.seekTo(player?.currentPosition ?: 0 + 5000)
    }

    override fun rewind() {
        if (player?.currentPosition ?: 0 < 5000) {
            player?.seekTo(player?.currentPosition ?: 0 - 5000)
        } else {
            player?.seekToDefaultPosition()
        }
    }

    override fun getCurrentPosition(): Long = player?.currentPosition ?: 0

    //fun getCurrentWindowIndex(): Int = player?.currentWindowIndex ?: 0

    override fun getPlayWhenReady(): Boolean = player?.playWhenReady ?: false

    override fun releasePlayer() {
        if (player != null) {
            player?.release()
            player = null
        }
    }

    private fun buildMediaSource(context: Context, uri: Uri): MediaSource {
        return ExtractorMediaSource.Factory(buildDataSource(context)).createMediaSource(uri)
    }

    private fun buildDataSource(context: Context): DataSource.Factory {
        val defaultBandwidthMeter = DefaultBandwidthMeter()
        return DefaultDataSourceFactory(context, Util.getUserAgent(context, "ExoPlayerDemo"), defaultBandwidthMeter)
    }
}