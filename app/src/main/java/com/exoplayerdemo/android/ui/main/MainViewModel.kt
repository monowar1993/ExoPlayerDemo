package com.exoplayerdemo.android.ui.main

import androidx.lifecycle.MutableLiveData
import com.exoplayerdemo.android.core.base.viewmodel.AppViewModel
import com.exoplayerdemo.android.data.repository.MediaRepository
import com.exoplayerdemo.android.data.repository.Video
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Mostafa Monowar at 20-May-19 2:37 AM
 * monowar1993@gmail.com
 */
class MainViewModel @Inject constructor(private val repository: MediaRepository) : AppViewModel() {

    val videosLiveData by lazy { MutableLiveData<List<Video>>() }

    fun getAllVideos() {
        compositeDisposable.add(
                repository.getAllVideos()
                        .doOnSubscribe { showLoader.postValue(true) }
                        .doAfterTerminate { showLoader.postValue(false) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ videosList ->
                            videosLiveData.postValue(videosList)
                        }, { throwable ->
                            throwable.printStackTrace()
                        })
        )
    }
}