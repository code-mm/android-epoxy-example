package com.ms.app.ui.fragment.message

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MessageRepository {

    private val messageList = mutableListOf<MessageModel>()


    fun getMessageList() = Observable.fromCallable<List<MessageModel>> {
        Thread.sleep(5000)
        messageList.addAll(listOf(
                MessageModel("张三", UUID.randomUUID().toString(), "_p2p", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("丰田科技园交友", UUID.randomUUID().toString(), "_group", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("系统通知", UUID.randomUUID().toString(), "_notification", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("张三", UUID.randomUUID().toString(), "_group", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("张三", UUID.randomUUID().toString(), "_p2p", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("张三", UUID.randomUUID().toString(), "_group", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("张三", UUID.randomUUID().toString(), "_p2p", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("张三", UUID.randomUUID().toString(), "_group", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("订单通知", UUID.randomUUID().toString(), "_notification", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("张三", UUID.randomUUID().toString(), "_group", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("张三", UUID.randomUUID().toString(), "_p2p", UUID.randomUUID().toString(), "在吗？😁", Date()),
                MessageModel("张三", UUID.randomUUID().toString(), "_group", UUID.randomUUID().toString(), "在吗？😁", Date())
        ))
        messageList
    }.subscribeOn(Schedulers.io())


}