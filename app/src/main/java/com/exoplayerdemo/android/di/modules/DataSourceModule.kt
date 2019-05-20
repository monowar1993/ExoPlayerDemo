package com.exoplayerdemo.android.di.modules

import com.exoplayerdemo.android.data.localsource.MediaLocalSource
import com.exoplayerdemo.android.data.localsource.MediaLocalSourceImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Mostafa Monowar on 06-Mar-19
 * Brain Station 23.
 */
@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindMediaLocalSource(mediaLocalSourceImpl: MediaLocalSourceImpl): MediaLocalSource
}