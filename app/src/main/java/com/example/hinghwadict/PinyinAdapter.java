package com.example.hinghwadict;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

public class PinyinAdapter extends RecyclerView.Adapter<PinyinAdapter.MyViewHolder> {
    private List<SearchPinyinResponse.character> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView character;
        public TextView traditional;
        public TextView area;
        private PronunciationAdapter pronunciationAdapter;
        private RecyclerView recyclerView;
        private LinearLayoutManager layoutManager;

        public void showPronunciation(List<SearchPinyinResponse.word> pronunciations) {
            pronunciationAdapter.setData(pronunciations);
        }

        public MyViewHolder(View v) {
            super(v);
            character = v.findViewById(R.id.character);
            traditional = v.findViewById(R.id.traditional);
            area = v.findViewById(R.id.area);

            recyclerView = v.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);

            //设置布局管理器
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(v.getContext());
            //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
            //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
            flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
            //justifyContent 属性定义了项目在主轴上的对齐方式。
            flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
            recyclerView.setLayoutManager(flexboxLayoutManager);

            pronunciationAdapter = new PronunciationAdapter();
            recyclerView.setAdapter(pronunciationAdapter);
        }
    }

    public void setData(List<SearchPinyinResponse.character> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PinyinAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pinyin_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        SearchPinyinResponse.character character = mDataset.get(position);
        holder.character.setText(character.label);
        if (!character.label.equals(character.traditional)) {
            holder.traditional.setVisibility(View.VISIBLE);
            holder.traditional.setText(character.traditional);
        } else {
            holder.traditional.setVisibility(View.GONE);
        }
        holder.area.setText(character.pronunciations.get(0).county + "/" + character.pronunciations.get(0).town);
        List<SearchPinyinResponse.word> pronunciations = character.pronunciations.get(0).words;
        holder.showPronunciation(pronunciations);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}