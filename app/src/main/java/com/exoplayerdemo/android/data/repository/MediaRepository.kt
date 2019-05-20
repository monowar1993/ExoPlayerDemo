package com.exoplayerdemo.android.data.repository

import io.reactivex.Single

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
interface MediaRepository {
    fun getAllVideos(): Single<List<Video>>
}