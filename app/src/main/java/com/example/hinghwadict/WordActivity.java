package com.example.hinghwadict;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordActivity extends AppCompatActivity {
    private TextView detail_word;
    private TextView detail_views;
    private TextView detail_pinyin;
    private TextView detail_ipa;

    private MyFragment1 myFragment1;
    private MyFragment2 myFragment2;

    public String id;

    String[] Title = {"释义", "其他"};
    List<Fragment> fragments;

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        Intent intent = getIntent();
        int r = intent.getIntExtra("random", 0);
        id = String.valueOf(r);

        detail_word = findViewById(R.id.detail_word);
        detail_views = findViewById(R.id.detail_views);
        detail_pinyin = findViewById(R.id.detail_pinyin);
        detail_ipa = findViewById(R.id.detail_ipa);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        fragments = new ArrayList<>();

        myFragment1 = new MyFragment1();
        myFragment2 = new MyFragment2();
        fragments.add(myFragment1);
        fragments.add(myFragment2);

        viewPager.setAdapter(new myAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);

        getWord();
    }

    private void getWord() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pxm.edialect.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getWordDetail(id).enqueue(new Callback<WordDetailResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WordDetailResponse> call, Response<WordDetailResponse> response) {
                if (response.body() != null) {
                    WordDetailResponse.Word word = response.body().word;
                    detail_word.setText(word.word);
                    detail_views.setText("访问：" + word.views);
                    detail_pinyin.setText(word.standard_pinyin);
                    detail_ipa.setText("/" + word.standard_ipa + "/");
                    myFragment1.setDefinition(word.definition);
                    myFragment2.setOther(word.annotation);
                }
            }

            @Override
            public void onFailure(Call<WordDetailResponse> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }

    private class myAdapter extends FragmentPagerAdapter {
        private List<Fragment> myFragment;

        public myAdapter(@NonNull FragmentManager fm, List<Fragment> fs) {
            super(fm);
            myFragment = fs;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return myFragment.get(position);
        }

        @Override
        public int getCount() {
            return myFragment.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return Title[position];
        }
    }

    public static class MyFragment1 extends Fragment {
        TextView detail_definition;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.definition_page, container, false);
            detail_definition = view.findViewById(R.id.detail_definition);
            return view;
        }

        public void setDefinition(String definition) {
            detail_definition.setText(definition);
        }
    }

    public static class MyFragment2 extends Fragment {
        TextView detail_other;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.other_page, container, false);
            detail_other = view.findViewById(R.id.detail_other);
            return view;
        }

        public void setOther(String other) {
            detail_other.setText(other);
        }
    }
}

















