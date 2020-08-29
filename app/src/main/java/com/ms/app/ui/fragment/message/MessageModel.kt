package com.ms.app.ui.fragment.message

import java.util.*

data class MessageModel(
        val roomName: String,
        val roomId: String,
        var roomType: String,
        val sender: String,
        var latestMessage: String,
        var datetime: Date
)