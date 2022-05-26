package com.example.hinghwadict;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private int id;
    private TextView random_word;
    private TextView word_word;
    private TextView definition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView cover = findViewById(R.id.cover);
        String cover_url = "https://cos.edialect.top/miniprogram/fm.gif";
        Glide.with(this).load(cover_url).into(cover);

        random_word = findViewById(R.id.random_word);
        word_word = findViewById(R.id.word);
        definition = findViewById(R.id.definition);

        // 加载底部导航栏
        getBottomNavigationBar();

        // 获取每日一词
        getWord();
    }

    private void getBottomNavigationBar() {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_baseline_home_24, ""))
                .addItem(new BottomNavigationItem(R.drawable.ic_baseline_article_24, ""))
                .setFirstSelectedPosition(0)
                .setMode(BottomNavigationBar.MODE_SHIFTING_NO_TITLE)
                .setActiveColor("#FFFFFF")
                .setBarBackgroundColor("#42C1A9")
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void getWord() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pxm.edialect.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getHomeWord().enqueue(new Callback<WordDayResponse>() {
            @Override
            public void onResponse(Call<WordDayResponse> call, Response<WordDayResponse> response) {
                if (response.body() != null) {
                    if (response.body().errorCode != -1) {
                        WordDayResponse.Word word = response.body().word;
                        Log.d("retrofit", word.toString());
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

    public void startSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void getRandomWord(View view) {
        Random random = new Random();
        int r = random.nextInt(6106) + 1;
        Intent intent = new Intent(this, WordActivity.class);
        intent.putExtra("random", r);
        startActivity(intent);
    }

    public void getWordDetail(View view) {
        Intent intent = new Intent(this, WordActivity.class);
        intent.putExtra("random", id);
        startActivity(intent);
    }
}