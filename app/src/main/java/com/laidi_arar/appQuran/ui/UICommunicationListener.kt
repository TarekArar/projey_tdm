package com.laidi_arar.appQuran.ui

import com.laidi_arar.appQuran.util.Response
import com.laidi_arar.appQuran.util.StateMessageCallback

/**
 * @author Mazrou Ayoub
 */
interface UICommunicationListener {
    fun onResponseReceived(
        response: Response<Any?>?,
        stateMessageCallback: StateMessageCallback
    )

    fun displayProgressBar(isLoading: Boolean, useDialog: Boolean = false)


    fun hideSoftKeyboard()


}