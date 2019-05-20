package com.exoplayerdemo.android.di.modules

import com.exoplayerdemo.android.data.repository.MediaRepository
import com.exoplayerdemo.android.data.repository.MediaRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Mostafa Monowar at 16-Jan-19 11:52 PM
 * monowar1993@gmail.com
 */
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindMediaRepositoryImpl(mediaRepositoryImpl: MediaRepositoryImpl): MediaRepository
}