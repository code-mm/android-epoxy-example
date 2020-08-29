package com.ms.app.ui.fragment.nearby

import com.airbnb.mvrx.fragmentViewModel
import com.ms.app.R;
import com.ms.app.ui.base.BaseFragment
import com.ms.app.ui.base.simpleController

class NearbyFragment : BaseFragment() {

    private val nearbyFragmentViewModel: NearbyFragmentViewModel by fragmentViewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_nearby
    }

    override fun epoxyController() = simpleController {

    }

    override fun invalidate() {}
}