package com.exoplayerdemo.android.data.localsource

import android.content.Context
import android.provider.MediaStore
import com.exoplayerdemo.android.data.repository.Video
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Mostafa Monowar on 20-May-19
 * Brain Station 23.
 */
class MediaLocalSourceImpl @Inject constructor(private val appContext: Context) : MediaLocalSource {

    override fun getAllVideos(): Single<List<Video>> {
        val videoHashSet = HashSet<Video>()
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME)
        //val orderBy = MediaStore.Video.Media.DATE_TAKEN
        val cursor = appContext.contentResolver.query(uri, projection, null, null, /*"$orderBy DESC"*/null)
        return Single.create { emitter ->
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    videoHashSet.add(Video(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))))
                }
                cursor.close()
                emitter.onSuccess(videoHashSet.toList())
            } else {
                emitter.onError(Exception("Cursor in null"))
            }
        }
    }
}