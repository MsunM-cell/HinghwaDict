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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView pinyin;
        public TextView ipa;

        public MyViewHolder(View v) {
            super(v);
            pinyin = v.findViewById(R.id.pinyin1);
            ipa = v.findViewById(R.id.ipa1);
        }
    }

    public void setData(List<SearchPinyinResponse.word> myDataset) {
        Log.d("pronunciation", String.valueOf(myDataset));
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PronunciationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pronunciation_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Log.d("dataset1", String.valueOf(position));
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d("mData", String.valueOf(mDataset.get(0).pinyin));
        holder.pinyin.setText(mDataset.get(position).pinyin);
        holder.ipa.setText("/" + mDataset.get(position).ipa + "/");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}