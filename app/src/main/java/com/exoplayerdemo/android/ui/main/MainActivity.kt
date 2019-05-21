package com.exoplayerdemo.android.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.exoplayerdemo.android.R
import com.exoplayerdemo.android.core.base.activity.AppActivity
import com.exoplayerdemo.android.databinding.ActivityMainBinding
import com.exoplayerdemo.android.ui.videoplayer.VideoPlayerActivity
import com.exoplayerdemo.android.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dimen
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MainActivity : AppActivity<MainViewModel, ActivityMainBinding>() {

    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSION = 100
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun createViewModel(): MainViewModel = ViewModelProviders.of(getActivity(), factory).get(MainViewModel::class.java)

    override fun setViewModel(binding: ActivityMainBinding, viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        getAllVideos()

        recyclerView.layoutManager = LinearLayoutManager(getContext())
        recyclerView.addItemDecoration(SpaceItemDecoration(dimen(R.dimen.fab_margin), true))
        recyclerView.setHasFixedSize(true)

        viewModel.videosLiveData.observe(getActivity(), Observer { videosList ->
            val adapter = VideoAdapter(getContext(), videosList.toMutableList())
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener { position ->
                val item = adapter.getItem(position)
                startActivity(Intent(getActivity(), VideoPlayerActivity::class.java).apply {
                    putExtra(VideoPlayerActivity.EXTRA_VIDEO_VIDEO_PATH, item.path)
                })
            }
        })

        viewModel.showLoader.observe(getActivity(), Observer {
            if (it != null && it) showLoader()
            else hideLoader()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(REQUEST_CODE_STORAGE_PERMISSION)
    private fun getAllVideos() {
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(getContext(), *permissions)) {
            viewModel.getAllVideos()
        } else {
            EasyPermissions.requestPermissions(getActivity(), getString(R.string.storage_rational), REQUEST_CODE_STORAGE_PERMISSION, *permissions)
        }
    }
}
