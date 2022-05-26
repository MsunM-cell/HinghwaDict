package com.example.hinghwadict;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.InternalStyleSheet;
import br.tiagohm.markdownview.css.styles.Github;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity {
    private MarkdownView mMarkdownView;
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IlNwaWRlciIsImlkIjo2LCJleHAiOjE2NTM1NjY1NDMuMzgwMDcxfQ.sbL2hHVuTiOafc_eW1yv7o2PV6mLCw5rCqytddxMupo";

    private TextView title;
    private TextView publish_time;
    private TextView views;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        // 获取文章的标题、发布时间、访问量和简介对应的组件
        title = findViewById(R.id.article_title);
        publish_time = findViewById(R.id.article_publish_time);
        views = findViewById(R.id.article_views);
        description = findViewById(R.id.article_description);

        // 获取文章的有用信息
        getArticle(99);
    }

    private void getArticle(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pxm.edialect.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        // 发起网络请求
        apiService.getArticle(String.valueOf(id), token).enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.body() != null) {
                    ArticleResponse.Article article = response.body().article;
                    // 设置文章的标题、发布时间、访问量和简介
                    title.setText(article.title);
                    publish_time.setText("发布：" + article.publish_time);
                    views.setText("访问：" + article.views);
                    description.setText(article.description);
                    // 基于Markdown文本进行展示
                    displayArticle(article.content);
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }

    private void displayArticle(String content) {
        // 设置文本样式
        InternalStyleSheet css = new Github();
        css.addMedia("screen and (min-width: 1281px)");

        mMarkdownView = (MarkdownView) findViewById(R.id.markdown_view);
        mMarkdownView.addStyleSheet(css);

        // 加载Markdown文本
        mMarkdownView.loadMarkdown(content);
    }
}