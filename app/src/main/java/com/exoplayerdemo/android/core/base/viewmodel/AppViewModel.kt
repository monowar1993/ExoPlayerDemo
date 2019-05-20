package com.exoplayerdemo.android.core.base.viewmodel

import androidx.lifecycle.ViewModel
import com.exoplayerdemo.android.core.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Mostafa Monowar on 9/7/18.
 * Brain Station 23.
 */

abstract class AppViewModel : ViewModel() {
    internal val showLoader by lazy { SingleLiveEvent<Boolean>() }
    internal val toastMessage by lazy { SingleLiveEvent<String>() }

    internal val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}