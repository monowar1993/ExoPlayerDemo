package com.exoplayerdemo.android.core.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.exoplayerdemo.android.core.viewmodel.AppViewModel
import kotlin.properties.Delegates

/**
 * Created by Mostafa Monowar on 9/7/18.
 * Brain Station 23.
 */

abstract class AppActivity<ViewModel : AppViewModel, DataBinding : ViewDataBinding> : BaseActivity<DataBinding>() {

    protected var viewModel: ViewModel by Delegates.notNull()

    /**
     * Override to create and set viewModel
     * @return view model instance
     */
    protected abstract fun createViewModel(): ViewModel

    /**
     * Override to set viewModel to dataBinding
     */
    protected abstract fun setViewModel(binding: DataBinding, viewModel: ViewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
        setViewModel(dataBinding, viewModel)
    }
}