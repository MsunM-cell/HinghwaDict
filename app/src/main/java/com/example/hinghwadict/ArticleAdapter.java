package com.example.hinghwadict;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {
    private List<HotArticlesResponse.HotArticles> mDataset;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;

        public MyViewHolder(View v) {
            super(v);
            cover = v.findViewById(R.id.cover_item);
        }
    }

    public ArticleAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<HotArticlesResponse.HotArticles> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ArticleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(mContext).load(mDataset.get(position).article.cover).into(holder.cover);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                HotArticlesResponse.Article article = mDataset.get(position).article;

                Intent intent = new Intent(v.getContext(), ArticleActivity.class);
                intent.putExtra("article", article.id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}