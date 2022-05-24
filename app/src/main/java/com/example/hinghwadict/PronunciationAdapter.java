package com.example.hinghwadict;

import android.content.Intent;
import android.graphics.Color;
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

        if (mDataset.get(position).word != 0) {
            holder.pinyin.setTextColor(Color.parseColor("#71C3AD"));
            holder.ipa.setTextColor(Color.parseColor("#71C3AD"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                SearchPinyinResponse.word w = mDataset.get(position);
                if (w.word != 0) {
                    Intent intent = new Intent(v.getContext(), WordActivity.class);
                    intent.putExtra("random", w.word);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}