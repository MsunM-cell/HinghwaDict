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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

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

    // Create new views (invoked by the layout manager)
    @Override
    public WordAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("dataset", String.valueOf(position));
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.word.setText(mDataset.get(position).word);
        holder.pinyin.setText(mDataset.get(position).standard_pinyin);
        holder.ipa.setText("/" + mDataset.get(position).standard_ipa + "/");
        holder.definition.setText(mDataset.get(position).definition);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("click", String.valueOf(holder.getAdapterPosition()));
                int position = holder.getAdapterPosition();
                SearchWordResponse.word w = mDataset.get(position);
                Log.d("word", String.valueOf(w));
//                Intent intent = new Intent(v.getContext(), SearchActivity.class);
//                v.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}