package com.exoplayerdemo.android.core.base.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.exoplayerdemo.android.core.App
import dagger.android.AndroidInjection
import dmax.dialog.SpotsDialog
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

/**
 * Created by Mostafa Monowar Shaon on 12/11/2016.
 * Brain Station-23.
 */
abstract class BaseActivity<DataBinding : ViewDataBinding> : AppCompatActivity() {

    protected val app: App by lazy { App.getApp(this) }

    protected var dataBinding: DataBinding by Delegates.notNull()

    protected var showAuthentication = true

    private val loaderDialog: AlertDialog by lazy { SpotsDialog.Builder().setContext(getActivity()).build() }

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    protected abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(getActivity())
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(getActivity(), layoutResourceId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getContext(): Context = this

    fun getActivity(): AppCompatActivity = this

    fun showLoader() {
        runOnUiThread { if (!loaderDialog.isShowing) loaderDialog.show() }
    }

    fun hideLoader() {
        runOnUiThread { if (loaderDialog.isShowing) loaderDialog.dismiss() }
    }

    fun showToast(message: String?) {
        message?.let { runOnUiThread { toast(it) } }
    }
}
