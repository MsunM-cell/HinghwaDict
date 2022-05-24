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

public class SearchActivity extends AppCompatActivity implements OnOptionPickedListener {
    private TextView hint1, hint2;
    private EditText searchBar;
    private OptionPicker picker;
    private TextView category;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private WordAdapter wordAdapter;

    private RecyclerView recyclerView1;
    private PinyinAdapter pinyinAdapter;
    private LinearLayoutManager layoutManager1;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        hint1 = findViewById(R.id.hint1);
        hint2 = findViewById(R.id.hint2);

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
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view_1);
        recyclerView1.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager1);

        wordAdapter = new WordAdapter();
        pinyinAdapter = new PinyinAdapter();

        recyclerView.setAdapter(wordAdapter);
        recyclerView1.setAdapter(pinyinAdapter);
    }

    public void selectCategory(View view) {
        List<category> data = new ArrayList<>();
        data.add(new category(1, "拼音"));
        data.add(new category(2, "词语"));
        picker = new OptionPicker(this);
        picker.setBodyWidth(140);
        picker.setData(data);
        picker.setDefaultPosition(0);
        picker.setOnOptionPickedListener(this);
        OptionWheelLayout wheelLayout = picker.getWheelLayout();
        wheelLayout.setIndicatorEnabled(false);
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
        String item_string = item.toString();
        category.setText(item_string);
        if (item_string.equals("词语")) {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView1.setVisibility(View.GONE);
            hint1.setVisibility(View.GONE);
            hint2.setVisibility(View.GONE);
        } else if (item_string.equals("拼音")) {
            recyclerView.setVisibility(View.GONE);
            recyclerView1.setVisibility(View.VISIBLE);
            hint1.setVisibility(View.VISIBLE);
            hint2.setVisibility(View.VISIBLE);
        }
    }

    private void search() {
        String category_string = category.getText().toString();
        String key_word = searchBar.getText().toString();
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
                    if (words.size() != 0) {
                        wordAdapter.setData(response.body().words);
                        wordAdapter.notifyDataSetChanged();
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














