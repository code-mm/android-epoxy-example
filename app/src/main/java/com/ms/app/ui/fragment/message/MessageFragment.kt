package com.ms.app.ui.fragment.message

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.*
import com.ms.app.R
import com.ms.app.messageRoomGroup
import com.ms.app.messageRoomNotification
import com.ms.app.messageRoomP2p
import com.ms.app.ui.base.BaseFragment
import com.ms.app.ui.base.simpleController
import com.ms.app.ui.fragment.message.item.messageEmptyItem
import kotlinx.android.synthetic.main.fragment_message.*
import java.text.SimpleDateFormat
import java.util.*

class MessageFragment : BaseFragment() {

    private val TAG = "MessageFragment"

    private val messageFragmentViewModel: MessageFragmentViewModel by fragmentViewModel()
    private val menus: MutableList<RightPopupWindowMenu.MenuItem> = ArrayList()
    private var rightPopupWindowMenu: RightPopupWindowMenu? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_message
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRightMenu()
        epoxyRecyclerViewMessage.setController(epoxyController)
    }

    private fun initRightMenu() {
        rightPopupWindowMenu = RightPopupWindowMenu(this.activity)
        rightPopupWindowMenu!!.setOnClickListener { menu ->
            rightPopupWindowMenu!!.dismiss()
            if ("扫一扫" == menu.name) {
                scan()
            }
        }
        menus.clear()
        val menuItemScan = RightPopupWindowMenu.MenuItem()
        menuItemScan.name = "扫一扫"
        menuItemScan.drawable = resources.getDrawable(R.drawable.image_scan)
        menus.add(menuItemScan)
        val menuItemAddFriend = RightPopupWindowMenu.MenuItem()
        menuItemAddFriend.name = "添加好友"
        menuItemAddFriend.drawable = resources.getDrawable(R.drawable.image_add_friend)
        menus.add(menuItemAddFriend)
        rightPopupWindowMenu!!.setData(menus)

        // 右键菜单点击事件
        imageViewMenu.setOnClickListener {
            rightPopupWindowMenu!!.showAsDropDown(imageViewMenu, -60, 30)
        }
    }

    private fun scan() {

    }

    override fun invalidate() {
        epoxyRecyclerViewMessage.requestModelBuild()

        withState(messageFragmentViewModel) { state ->
            when (state.model) {
                is Loading -> {
                    showDialog()
                    showToast("加载中...")
                }
                is Success -> {
                    hideDialog()
                    showToast("加载成功...")
                }
                is Fail -> {
                    hideDialog()
                    showToast("加载失败...")
                }
            }
        }
    }

    override fun epoxyController() = simpleController(messageFragmentViewModel) {

        state ->
        val invoke = state.model.invoke();
        if (invoke != null) {
            if (invoke.size == 0) {
                messageEmptyItem {
                    id(View.generateViewId())
                }
            } else {
                for (it in invoke) {
                    if (it.roomType.equals("_p2p")) {
                        messageRoomP2p {
                            id(View.generateViewId())
                            name(it.roomName)
                            latestMessage(it.latestMessage)
                            dateTime(SimpleDateFormat("HH:mm").format(it.datetime))
                            unreadMessageNumber("1")
                        }
                    } else if (it.roomType.equals("_group")) {
                        messageRoomGroup {
                            id(View.generateViewId())
                            name(it.roomName)
                            latestMessage(it.latestMessage)
                            dateTime(SimpleDateFormat("HH:mm").format(it.datetime))
                            unreadMessageNumber("1")
                        }
                    } else if (it.roomType.equals("_notification")) {
                        messageRoomNotification {
                            id(View.generateViewId())
                            name(it.roomName)
                            latestMessage(it.latestMessage)
                            dateTime(SimpleDateFormat("HH:mm").format(it.datetime))
                            unreadMessageNumber("1")
                        }
                    }

                }
            }
        }
    }
}