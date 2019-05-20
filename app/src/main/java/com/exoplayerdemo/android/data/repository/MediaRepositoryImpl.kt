package com.exoplayerdemo.android.data.repository

import com.exoplayerdemo.android.data.localsource.MediaLocalSource
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
class MediaRepositoryImpl @Inject constructor(private val localSource: MediaLocalSource) : MediaRepository {

    override fun getAllVideos(): Single<List<Video>> {
        return localSource.getAllVideos()
    }
}