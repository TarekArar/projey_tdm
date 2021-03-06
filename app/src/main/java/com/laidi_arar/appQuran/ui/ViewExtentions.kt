package com.laidi_arar.appQuran.ui

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import com.laidi_arar.appQuran.util.StateMessageCallback

/**
 * @author Mazrou Ayoub
 */

fun Activity.displayToast(
    @StringRes message: Int,
    stateMessageCallback: StateMessageCallback
) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    stateMessageCallback.removeMessageFromStack()
}

fun Activity.displayToast(
    message: String,
    stateMessageCallback: StateMessageCallback
) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    stateMessageCallback.removeMessageFromStack()
}


interface AreYouSureCallback {

    fun proceed()

    fun cancel()
}