package com.airbnb.mvrx.sample.core

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.ms.app.ui.fragment.message.MessageFragment


open class MessageMvRxEpoxyController(
        val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {
    override fun buildModels() {


    }

}


fun MessageFragment.simpleController(
        buildModels: EpoxyController.() -> Unit
) = MessageMvRxEpoxyController {
    // Models are built asynchronously, so it is possible that this is called after the fragment
    // is detached under certain race conditions.
    if (view == null || isRemoving) return@MessageMvRxEpoxyController
    buildModels()
}