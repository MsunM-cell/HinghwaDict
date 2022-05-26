package com.example.hinghwadict;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticlesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        // 基于RecyclerView进行文章列表展示
        recyclerView = (RecyclerView) findViewById(R.id.articles_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        articleAdapter = new ArticleAdapter(this);
        recyclerView.setAdapter(articleAdapter);

        // 获取热门文章
        getHotArticles();
    }

    private void getHotArticles() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pxm.edialect.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        // 发起网络请求
        apiService.getHotArticles().enqueue(new Callback<HotArticlesResponse>() {
            @Override
            public void onResponse(Call<HotArticlesResponse> call, Response<HotArticlesResponse> response) {
                if (response.body() != null) {
                    List<HotArticlesResponse.HotArticles> hotArticles = response.body().hotArticles;
                    articleAdapter.setData(hotArticles);
                    articleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<HotArticlesResponse> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }
}















