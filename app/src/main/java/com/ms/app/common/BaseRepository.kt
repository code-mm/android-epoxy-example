package com.ms.app.common

import com.ms.app.ui.fragment.addressbook.AddressBookRepository
import com.ms.app.ui.fragment.message.MessageRepository
import com.ms.app.ui.fragment.nearby.NearbyRepository

class BaseRepository {
    constructor() {}



    val messageRepository by lazy {
        MessageRepository()
    }

    val nearbyRepository by lazy {
        NearbyRepository()
    }
    val addressBookRepository by lazy {
        AddressBookRepository()
    }
}