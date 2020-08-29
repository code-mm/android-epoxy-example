package com.ms.app.ui.fragment.message.item

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.ModelView
import com.ms.app.R;

// 通知消息
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class RoomTypeNotification
@JvmOverloads constructor(context: Context?,
                          attrs: AttributeSet? = null,
                          defStyleAttr: Int = 0
) : android.widget.RelativeLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.item_recycler_view_message_room_notification, this)
    }
}