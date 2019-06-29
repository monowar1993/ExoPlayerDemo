package com.exoplayerdemo.android.ui.videoplayer

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.exoplayerdemo.android.R
import com.exoplayerdemo.android.core.base.activity.AppActivity
import com.exoplayerdemo.android.core.exoplayer.AppPLayer
import com.exoplayerdemo.android.core.exoplayer.AppPlayerImpl
import com.exoplayerdemo.android.databinding.ActivityVideoPlayerBinding
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.Util
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_video_player.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import javax.inject.Inject

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
class VideoPlayerActivity : AppActivity<VideoPlayerViewModel, ActivityVideoPlayerBinding>() {

    companion object {
        const val EXTRA_VIDEO_VIDEO_NAME = "name"
        const val EXTRA_VIDEO_VIDEO_PATH = "path"
        const val EXTRA_VIDEO_VIDEO_THUMBNAIL = "thumbnail"
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val appPlayer: AppPLayer by lazy { AppPlayerImpl() }
    private val playerStateChangeListener = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            val stateString: String
            when (playbackState) {
                Player.STATE_IDLE -> stateString = "ExoPlayer.STATE_IDLE"
                Player.STATE_BUFFERING -> stateString = "ExoPlayer.STATE_BUFFERING"
                Player.STATE_READY -> stateString = "ExoPlayer.STATE_READY"
                Player.STATE_ENDED -> {
                    stateString = "ExoPlayer.STATE_ENDED"
                    viewModel.playerFinishedPlaying = true
                    btnPlay.setImageResource(R.drawable.exo_controls_play)
                }
                else -> stateString = "UNKNOWN_STATE"
            }
            Logger.d("changed state to $stateString playWhenReady: $playWhenReady PlayWhenReady: " + appPlayer.getPlayWhenReady())
        }
    }

    private lateinit var videoPath: String

    override fun createViewModel(): VideoPlayerViewModel = ViewModelProviders.of(getActivity(), factory).get(VideoPlayerViewModel::class.java)

    override fun setViewModel(binding: ActivityVideoPlayerBinding, viewModel: VideoPlayerViewModel) {
        binding.viewModel = viewModel
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_video_player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videoPath = intent.getStringExtra(EXTRA_VIDEO_VIDEO_PATH)!!

        // Handle click on custom layout
        btnPlay.onClick {
            if (!viewModel.playerFinishedPlaying) {
                if (appPlayer.getPlayWhenReady()) {
                    appPlayer.playPause(false)
                    viewModel.playerIsPlaying = false
                    btnPlay.setImageResource(R.drawable.exo_controls_play)
                } else {
                    appPlayer.playPause(true)
                    viewModel.playerIsPlaying = true
                    btnPlay.setImageResource(R.drawable.exo_controls_pause)
                }
            } else {
                viewModel.playerFinishedPlaying = false
                viewModel.playerIsPlaying = true
                viewModel.playerCurrentPosition = 0
                appPlayer.play(getContext(), Uri.parse(videoPath), viewModel.playerIsPlaying, viewModel.playerCurrentPosition)
                btnPlay.setImageResource(R.drawable.exo_controls_pause)
            }
        }

        btnForward.onClick { appPlayer.forward() }

        btnRewind.onClick { appPlayer.rewind() }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUi()
        } else {
            showSystemUi()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            startPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23) {
            startPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            stopPlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            stopPlayer()
        }
    }

    private fun startPlayer() {
        playerView.player = appPlayer.initializePlayer(getContext())
        appPlayer.play(getContext(), Uri.parse(videoPath), viewModel.playerIsPlaying, viewModel.playerCurrentPosition)
        appPlayer.addListener(playerStateChangeListener)
        if (appPlayer.getPlayWhenReady()) {
            btnPlay.setImageResource(R.drawable.exo_controls_pause)
        } else {
            btnPlay.setImageResource(R.drawable.exo_controls_play)
        }
    }

    private fun stopPlayer() {
        viewModel.playerIsPlaying = appPlayer.getPlayWhenReady()
        viewModel.playerCurrentPosition = appPlayer.getCurrentPosition()
        appPlayer.removeListener(playerStateChangeListener)
        appPlayer.releasePlayer()
    }

    private fun hideSystemUi() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun showSystemUi() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}