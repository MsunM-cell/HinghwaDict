package com.example.hinghwadict;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.github.gzuliyujiang.wheelpicker.OptionPicker;
import com.github.gzuliyujiang.wheelpicker.contract.OnOptionPickedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnOptionSelectedListener;
import com.github.gzuliyujiang.wheelpicker.widget.OptionWheelLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

public class SearchActivity extends AppCompatActivity implements OnOptionPickedListener {
    private EditText searchBar;
    private OptionPicker picker;
    private TextView category;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private WordAdapter wordAdapter;
    private List<SearchWordResponse.word> mWords;

    private RecyclerView recyclerView1;
    private PinyinAdapter pinyinAdapter;
    private LinearLayoutManager layoutManager1;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        category = findViewById(R.id.category);

        searchBar = findViewById(R.id.searchBar);
        searchBar.requestFocus();
        searchBar.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchBar.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        searchBar.setSingleLine(true);

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    search();
                }
                return false;
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pxm.edialect.top/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_1);
        recyclerView1.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager1);

//        //设置布局管理器
//        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(SearchActivity.this);
//        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
//        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
//        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
//        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
//        //justifyContent 属性定义了项目在主轴上的对齐方式。
//        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
//
//        recyclerView.setLayoutManager(flexboxLayoutManager);


        // specify an adapter (see also next example)
//        mWords = new ArrayList<>();
        wordAdapter = new WordAdapter();
        pinyinAdapter = new PinyinAdapter();

        recyclerView.setAdapter(wordAdapter);
        recyclerView1.setAdapter(pinyinAdapter);
    }

    public void selectCategory(View view) {
        List<category> data = new ArrayList<>();
        data.add(new category(1, "词语"));
        data.add(new category(2, "拼音"));
        picker = new OptionPicker(this);
//        picker.setTitle("分类");
        picker.setBodyWidth(140);
        picker.setData(data);
        picker.setDefaultPosition(1);
        picker.setOnOptionPickedListener(this);
        OptionWheelLayout wheelLayout = picker.getWheelLayout();
        wheelLayout.setIndicatorEnabled(false);
//        wheelLayout.setTextColor(0xFFFF00FF);
//        wheelLayout.setSelectedTextColor(0xFFFF0000);
//        wheelLayout.setTextSize(15 * view.getResources().getDisplayMetrics().scaledDensity);
//        //注：建议通过`setStyle`定制样式设置文字加大，若通过`setSelectedTextSize`设置，该解决方案会导致选择器展示时跳动一下
//        //wheelLayout.setStyle(R.style.WheelStyleDemo);
//        wheelLayout.setSelectedTextSize(17 * view.getResources().getDisplayMetrics().scaledDensity);
//        wheelLayout.setSelectedTextBold(true);
//        wheelLayout.setCurtainEnabled(true);
//        wheelLayout.setCurtainColor(0xEEFF0000);
//        wheelLayout.setCurtainCorner(CurtainCorner.ALL);
//        wheelLayout.setCurtainRadius(5 * view.getResources().getDisplayMetrics().density);
        wheelLayout.setOnOptionSelectedListener(new OnOptionSelectedListener() {
            @Override
            public void onOptionSelected(int position, Object item) {
                picker.getTitleView().setText(picker.getWheelView().formatItem(position));
            }
        });
        picker.show();
    }

    @Override
    public void onOptionPicked(int position, Object item) {
        Log.d("xz", String.valueOf(item));
        category.setText(String.valueOf(item));
    }

    private void search() {
        String category_string = category.getText().toString();
        String key_word = searchBar.getText().toString();
        Log.d("search", "!!!!!!");
        if (category_string.equals("词语"))
            searchWord(key_word);
        else if (category_string.equals("拼音"))
            searchPinyin(key_word);
    }

    private void searchWord(String key_word) {
        apiService.getSearchWord(key_word).enqueue(new Callback<SearchWordResponse>() {
            @Override
            public void onResponse(Call<SearchWordResponse> call, Response<SearchWordResponse> response) {
                if (response.body() != null) {
                    List<SearchWordResponse.word> words = response.body().words;
                    Log.d("retrofit", words.toString());
                    if (words.size() != 0) {
                        wordAdapter.setData(response.body().words);
                        wordAdapter.notifyDataSetChanged();

//                        Log.d("dataset", String.valueOf(wordAdapter.getItemCount()));
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchWordResponse> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }

    private void searchPinyin(String key_word) {
        apiService.getSearchCharacter(key_word).enqueue(new Callback<SearchPinyinResponse>() {
            @Override
            public void onResponse(Call<SearchPinyinResponse> call, Response<SearchPinyinResponse> response) {
                if (response.body() != null) {
                    List<SearchPinyinResponse.character> characters = response.body().characters;

                    if (characters.size() != 0) {
                        pinyinAdapter.setData(response.body().characters);
                        pinyinAdapter.notifyDataSetChanged();

//                        Log.d("dataset", String.valueOf(wordAdapter.getItemCount()));
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchPinyinResponse> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }
}














