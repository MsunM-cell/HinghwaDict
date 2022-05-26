package com.example.hinghwadict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private int id;
    private TextView word_word;
    private TextView definition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 加载顶部封面gif
        ImageView cover = findViewById(R.id.cover);
        String cover_url = "https://cos.edialect.top/miniprogram/fm.gif";
        Glide.with(this).load(cover_url).into(cover);

        word_word = findViewById(R.id.word);
        definition = findViewById(R.id.definition);

        // 获取每日一词
        getWord();
    }

    private void getWord() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pxm.edialect.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        // 发起网络请求
        apiService.getHomeWord().enqueue(new Callback<WordDayResponse>() {
            @Override
            public void onResponse(Call<WordDayResponse> call, Response<WordDayResponse> response) {
                if (response.body() != null) {
                    if (response.body().errorCode != -1) {
                        WordDayResponse.Word word = response.body().word;
                        id = word.id;
                        word_word.setText(word.word);
                        definition.setText(word.definition);
                    }
                }
            }

            @Override
            public void onFailure(Call<WordDayResponse> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }

    // 转到搜索页面
    public void startSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    // 随机跳词
    public void getRandomWord(View view) {
        Random random = new Random();
        int r = random.nextInt(6106) + 1;
        Intent intent = new Intent(this, WordActivity.class);
        intent.putExtra("random", r);
        startActivity(intent);
    }

    // 进入词语详情页面
    public void getWordDetail(View view) {
        Intent intent = new Intent(this, WordActivity.class);
        intent.putExtra("random", id);
        startActivity(intent);
    }

    // 获取公告文章
    public void getAnnouncement(View view) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra("article", 175);
        startActivity(intent);
    }
}