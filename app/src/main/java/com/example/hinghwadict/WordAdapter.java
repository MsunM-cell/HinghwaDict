package com.example.hinghwadict;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.MyViewHolder> {
    private List<SearchWordResponse.word> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView word;
        public TextView pinyin;
        public TextView ipa;
        public TextView definition;

        public MyViewHolder(View v) {
            super(v);
            word = v.findViewById(R.id.word);
            pinyin = v.findViewById(R.id.pinyin);
            ipa = v.findViewById(R.id.ipa);
            definition = v.findViewById(R.id.definition);
        }
    }

    public void setData(List<SearchWordResponse.word> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public WordAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.word.setText(mDataset.get(position).word);
        holder.pinyin.setText(mDataset.get(position).standard_pinyin);
        holder.ipa.setText("/" + mDataset.get(position).standard_ipa + "/");
        holder.definition.setText(mDataset.get(position).definition);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                SearchWordResponse.word w = mDataset.get(position);

                Intent intent = new Intent(v.getContext(), WordActivity.class);
                intent.putExtra("random", w.id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}