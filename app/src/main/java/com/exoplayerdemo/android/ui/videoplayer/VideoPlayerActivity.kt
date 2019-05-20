package com.exoplayerdemo.android.ui.videoplayer

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.exoplayerdemo.android.R
import com.exoplayerdemo.android.core.base.activity.AppActivity
import com.exoplayerdemo.android.core.exoplayer.AppPlayerImpl
import com.exoplayerdemo.android.databinding.ActivityVideoPlayerBinding
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_video_player.*

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
class VideoPlayerActivity: AppActivity<VideoPlayerViewModel, ActivityVideoPlayerBinding>() {

    companion object {
        const val EXTRA_VIDEO_VIDEO_NAME = "name"
        const val EXTRA_VIDEO_VIDEO_PATH = "path"
        const val EXTRA_VIDEO_VIDEO_THUMBNAIL = "thumbnail"
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val appPlayer by lazy { AppPlayerImpl() }

    private lateinit var videoPath: String

    override fun createViewModel(): VideoPlayerViewModel = ViewModelProviders.of(getActivity(), factory).get(VideoPlayerViewModel::class.java)

    override fun setViewModel(binding: ActivityVideoPlayerBinding, viewModel: VideoPlayerViewModel) {
        binding.viewModel = viewModel
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_video_player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videoView.player = appPlayer.initializePlayer(getContext())

        videoPath = intent.getStringExtra(EXTRA_VIDEO_VIDEO_PATH)

        appPlayer.play(getContext(), Uri.parse(videoPath))
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        hideSystemUi()
    }

    private fun hideSystemUi() {
        videoView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }
}