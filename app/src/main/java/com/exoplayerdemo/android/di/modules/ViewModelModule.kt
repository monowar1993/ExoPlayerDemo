package com.exoplayerdemo.android.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dignio.android.di.ViewModelKey
import com.exoplayerdemo.android.core.viewmodel.AppViewModelFactory
import com.exoplayerdemo.android.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Mostafa Monowar on 9/7/18.
 * Brain Station 23.
 */


/**
 * All ViewModel classes that uses Dagger2 injection, must be declared here to support constructor injection,
 * otherwise app will give following exception on runtime access:
 * IllegalArgumentException: "unknown model call class"
 */

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}