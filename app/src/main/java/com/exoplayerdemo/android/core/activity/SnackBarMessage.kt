package com.dignio.android.core.base.activity

/**
 * Created by Mostafa Monowar on 9/24/18.
 * Brain Station 23.
 */
data class SnackBarMessage(var message: String?, var messageType: Int = SNACK_BAR_NORMAL) {
    companion object {
        const val SNACK_BAR_SUCCESS = 1
        const val SNACK_BAR_ERROR = 0
        const val SNACK_BAR_NORMAL = 2
    }
}