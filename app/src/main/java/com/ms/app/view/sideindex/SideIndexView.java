package com.ms.app.view.sideindex;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ms.app.R;;
import com.bdlbsc.doper.utils.pinyin.PinyinUtils;

import java.util.List;


public class SideIndexView extends LinearLayout {

    private RecyclerView recyclerView;

    private IndexView indexView;

    private View view;

    private List<SideItem> datas;

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

    public SideIndexView(Context context) {
        this(context, null);
    }

    public SideIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = LayoutInflater.from(context).inflate(R.layout.view_addressbook, this);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recyclerView);
        indexView = view.findViewById(R.id.indexView);
    }

    public void setpersonList(List<SideItem> datas) {
        this.datas = datas;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new AddressBookAdapter());
        indexView.setIndexViewLetterChangeListener(new IndexView.IndexViewLetterChangeListener() {
            @Override
            public void onIndexLetterChange(String letter) {


                updateRecyclerView(letter);

            }
        });
    }

    private void updateRecyclerView(String letter) {

        for (int i = 0; i < datas.size(); i++) {
            String let = PinyinUtils.INSTANCE.hanZi2Pinyin(datas.get(i).getName()).substring(0, 1);
            if (let.equals("#")) {
                MoveToPosition(linearLayoutManager, 0);
            }
            if (let.equals(letter)) {
                MoveToPosition(linearLayoutManager, i);
                return;
            }
        }
    }

    /**
     * 设置RecyclerView 到指定位置
     *
     * @param manager
     * @param n
     */
    public static void MoveToPosition(LinearLayoutManager manager, int n) {
        manager.scrollToPositionWithOffset(n, 0);
        manager.setStackFromEnd(true);
    }

    public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_addressbook_recyclerview_adapter, parent, false);


            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final SideItem person = datas.get(position);



            String letter = PinyinUtils.INSTANCE.hanZi2Pinyin(person.getName()).substring(0, 1);
            String name = person.getName();

            if (letter.toCharArray()[0] < 'A' || letter.toCharArray()[0] > 'Z') {

                holder.textViewLetter.setText("#");
                holder.textViewName.setText(name);
            } else {

                holder.textViewLetter.setText(letter);
                holder.textViewName.setText(name);

            }


            if (position == 0) {
                holder.textViewLetter.setVisibility(View.VISIBLE);
            } else {


                String lett = PinyinUtils.INSTANCE.hanZi2Pinyin(datas.get(position - 1).getName()).substring(0, 1);

                if (letter.equals(lett)) {

                    holder.textViewLetter.setVisibility(View.GONE);

                } else {
                    holder.textViewLetter.setVisibility(View.VISIBLE);
                }
                // 对#的处理
                if (lett.toCharArray()[0] < 'A' || lett.toCharArray()[0] > 'Z') {
                    lett = "#";
                    if (letter.toCharArray()[0] < 'A' || letter.toCharArray()[0] > 'Z') {
                        letter = "#";
                        if (letter.equals(lett)) {
                            holder.textViewLetter.setVisibility(View.GONE);
                        } else {
                            holder.textViewLetter.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            holder.textViewName.setText(person.getName());

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView textViewLetter;
            private ImageView imageViewAvatar;
            private TextView textViewName;

            public ViewHolder(View itemView) {
                super(itemView);
                textViewLetter = itemView.findViewById(R.id.textViewLetter);
                textViewName = itemView.findViewById(R.id.textViewName);
                imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            }
        }
    }
}