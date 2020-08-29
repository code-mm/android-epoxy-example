package com.ms.app.ui.fragment.my

import com.ms.app.R;
import com.ms.app.ui.base.BaseFragment
import com.ms.app.ui.base.simpleController

class MyFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_my
    }

    override fun epoxyController() = simpleController {

    }

    override fun invalidate() {}
}