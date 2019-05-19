package com.exoplayerdemo.android.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * Created by Mostafa Monowar on 9/7/18.
 * Brain Station 23.
 */

@Module
abstract class AppModule {

    @Binds
    abstract fun bindApplicationContext(application: Application): Context

    /*@Binds
    abstract fun bindSession(sharedPreferenceSessionImpl: SharedPreferenceSessionImpl): Session

    @Binds
    abstract fun bindValidator(validatorImpl: ValidatorImpl): Validator

    @Module
    companion object {
        @JvmStatic
        @Singleton
        @Provides
        fun provideRxBleClient(@ApplicationContext context: Context): RxBleClient = RxBleClient.create(context)
    }*/
}