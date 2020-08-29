package com.ms.app.ui.fragment.message

import com.airbnb.mvrx.*
import com.ms.app.app.App
import com.ms.app.ui.base.MvRxViewModel

class MessageFragmentViewModel(
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

    companion object : MvRxViewModelFactory<MessageFragmentViewModel, MessageState> {
        override fun create(
                viewModelContext: ViewModelContext,
                state: MessageState
        ): MessageFragmentViewModel? {
            val repository =
                    viewModelContext.app<App>().baseRepository.messageRepository
            return MessageFragmentViewModel(
                    state,
                    repository
            )
        }
    }
}

