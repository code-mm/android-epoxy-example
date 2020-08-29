package com.ms.app.ui.fragment.addressbook

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized


data class AddressBookState(

        val model: Async<List<AddressBookModel>> = Uninitialized

) : MvRxState

