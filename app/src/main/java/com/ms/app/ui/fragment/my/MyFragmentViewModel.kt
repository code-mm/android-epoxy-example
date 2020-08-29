package com.ms.app.ui.fragment.my

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.ms.app.app.App
import com.ms.app.ui.base.MvRxViewModel
import com.ms.app.ui.fragment.message.MessageRepository
import com.ms.app.ui.fragment.message.MessageState


class MyFragmentViewModel(
        initialState: MessageState,
        private val messageRepository: MessageRepository
) : MvRxViewModel<MessageState>(initialState) {

    init {
        setState {
            copy(model = Loading())
        }
        messageRepository.getMessageList().execute {
            copy(model = it)

        }

    }

    companion object : MvRxViewModelFactory<MyFragmentViewModel, MessageState> {
        override fun create(
                viewModelContext: ViewModelContext,
                state: MessageState
        ): MyFragmentViewModel? {
            val repository =
                    viewModelContext.app<App>().baseRepository.messageRepository
            return MyFragmentViewModel(
                    state,
                    repository
            )
        }
    }
}

