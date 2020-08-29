package com.ms.app.ui.fragment.addressbook

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.ms.app.app.App
import com.ms.app.ui.base.MvRxViewModel


class AddressBookFragmentViewModel(
        initialState: AddressBookState,
        private val addressBookRepository: AddressBookRepository
) : MvRxViewModel<AddressBookState>(initialState) {

    init {
        setState {
            copy(model = Loading())
        }


    }

    companion object : MvRxViewModelFactory<AddressBookFragmentViewModel, AddressBookState> {
        override fun create(
                viewModelContext: ViewModelContext,
                state: AddressBookState
        ): AddressBookFragmentViewModel? {
            val repository =
                    viewModelContext.app<App>().baseRepository.addressBookRepository
            return AddressBookFragmentViewModel(
                    state,
                    repository
            )
        }
    }
}

