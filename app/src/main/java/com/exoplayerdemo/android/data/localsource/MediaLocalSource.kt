package com.exoplayerdemo.android.data.localsource

import com.exoplayerdemo.android.data.repository.Video
import io.reactivex.Single

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
interface MediaLocalSource {
    fun getAllVideos(): Single<List<Video>>
}