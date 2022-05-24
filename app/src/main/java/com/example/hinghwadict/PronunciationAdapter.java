package com.example.hinghwadict;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PronunciationAdapter extends RecyclerView.Adapter<PronunciationAdapter.MyViewHolder> {
    private List<SearchPinyinResponse.word> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pinyin;
        public TextView ipa;

        public MyViewHolder(View v) {
            super(v);
            pinyin = v.findViewById(R.id.pinyin1);
            ipa = v.findViewById(R.id.ipa1);
        }
    }

    public void setData(List<SearchPinyinResponse.word> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public PronunciationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pronunciation_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.pinyin.setText(mDataset.get(position).pinyin);
        holder.ipa.setText("/" + mDataset.get(position).ipa + "/");
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}