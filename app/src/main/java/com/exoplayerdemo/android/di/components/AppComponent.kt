package com.exoplayerdemo.android.di.components

import android.app.Application
import com.exoplayerdemo.android.core.App
import com.exoplayerdemo.android.di.builders.ActivityBuilder
import com.exoplayerdemo.android.di.modules.AppModule
import com.exoplayerdemo.android.di.modules.DataSourceModule
import com.exoplayerdemo.android.di.modules.RepositoryModule
import com.exoplayerdemo.android.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Mostafa Monowar on 9/7/18.
 * Brain Station 23.
 */

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class,
        ViewModelModule::class,
        RepositoryModule::class,
        DataSourceModule::class]
)
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}