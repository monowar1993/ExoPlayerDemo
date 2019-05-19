package com.exoplayerdemo.android.ui.main

import android.Manifest
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.exoplayerdemo.android.R
import com.exoplayerdemo.android.core.activity.AppActivity
import com.exoplayerdemo.android.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
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

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
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
            val videoItemHashSet = HashSet<String>()
            val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME)
            //val orderBy = MediaStore.Video.Media.DATE_TAKEN
            val cursor = getContext().contentResolver.query(uri, projection, null, null, /*"$orderBy DESC"*/null)
            try {
                cursor!!.moveToFirst()
                do {
                    videoItemHashSet.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)))
                } while (cursor.moveToNext())

                cursor.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            //return ArrayList(videoItemHashSet)
            Logger.d(videoItemHashSet.toString())
        } else {
            EasyPermissions.requestPermissions(getActivity(), getString(R.string.storage_rational), REQUEST_CODE_STORAGE_PERMISSION, *permissions)
        }
    }
}
