package com.ms.app.ui.fragment.message

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class MessageState(

        val model: Async<List<MessageModel>> = Uninitialized

) : MvRxState

