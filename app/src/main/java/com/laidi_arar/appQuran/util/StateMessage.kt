package com.laidi_arar.appQuran.util

import com.laidi_arar.appQuran.ui.AreYouSureCallback

data class StateMessage(val response: Response<Any?>)

data class Response<T>(
    val message: String?,
    val uiComponentType: UIComponentType,
    val messageType: MessageType
)

sealed class UIComponentType{
    class Toast: UIComponentType()


    class Dialog: UIComponentType()


    class AreYouSureDialog(
        val callback: AreYouSureCallback,
        ): UIComponentType()

    class None: UIComponentType()

}

sealed class MessageType{

    class Success: MessageType()

    class Error: MessageType()

    class Info: MessageType()

    class None: MessageType()
}
interface StateMessageCallback{
    fun removeMessageFromStack()
}