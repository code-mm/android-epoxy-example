package com.ms.app.ui.fragment.nearby

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class NearbyState(

        val model: Async<List<NearbyModel>> = Uninitialized

) : MvRxState

