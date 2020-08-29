package com.ms.app.ui.fragment.message;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ms.app.R;;

import java.util.ArrayList;
import java.util.List;

public class RightPopupWindowMenu extends PopupWindow {

    private Activity context;

    private ListView listView;


    private List<MenuItem> menus = new ArrayList<>();
    private final ListViewAdapter adapter;

    public RightPopupWindowMenu(Activity context) {

        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.menu_popupwindow_rightmenu, null);
        setContentView(view);

        listView = view.findViewById(R.id.listView);
        adapter = new ListViewAdapter(context, menus);
        listView.setAdapter(adapter);

        // 为了避免部分机型不显示，我们需要重新设置一下宽高
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        setAnimationStyle(R.style.rightPopUpWindowMenu);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        setOutsideTouchable(true);

        // 设置pop关闭监听，用于改变背景透明度
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
    }

    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgcolor;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


    public void setData(List<MenuItem> menus) {
        this.menus.clear();
        this.menus.addAll(menus);
        adapter.notifyDataSetChanged();
    }

    public static class ListViewAdapter extends BaseAdapter {


        private Context context;
        private List<MenuItem> menuItemList;
        OnClickListener onClickListener;

        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public ListViewAdapter(Context context, List<MenuItem> menuItemList) {
            this.context = context;
            this.menuItemList = menuItemList;
        }

        @Override
        public int getCount() {
            return menuItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (holder == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_popupwindow_rightmenu_listview, null);
                holder.imageViewIcon = convertView.findViewById(R.id.imageViewIcon);
                holder.textViewName = convertView.findViewById(R.id.textViewName);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.imageViewIcon.setImageDrawable(menuItemList.get(position).drawable);
            holder.textViewName.setText(menuItemList.get(position).name);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(menuItemList.get(position));
                    }

                }
            });

            return convertView;
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        adapter.setOnClickListener(onClickListener);
    }

    public static interface OnClickListener {
        void onClick(MenuItem menu);
    }

    private static class ViewHolder {
        private ImageView imageViewIcon;
        private TextView textViewName;
    }

    public static class MenuItem {
        String name;
        Drawable drawable;
    }

    @Override
    public void showAsDropDown(View anchor) {
        darkenBackground(0.5f);
        super.showAsDropDown(anchor);

    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        darkenBackground(0.5f);
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }
}
