package com.example.hinghwadict;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView cover = findViewById(R.id.cover);
        String cover_url = "https://cos.edialect.top/miniprogram/fm.gif";
        Glide.with(this).load(cover_url).into(cover);
    }
}