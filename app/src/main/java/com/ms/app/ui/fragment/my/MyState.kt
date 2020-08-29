package com.ms.app.ui.fragment.my

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized


data class MyState(
        val model: Async<List<MyModel>> = Uninitialized
) : MvRxState

