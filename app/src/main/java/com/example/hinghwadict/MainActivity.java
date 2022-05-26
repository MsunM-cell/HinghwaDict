package com.example.hinghwadict;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LocalActivityManager activity_manger;
    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewPager = findViewById(R.id.view_pager);

        // 获取底部导航栏
        getBottomNavigationBar();
        // 为viewPager设置适配器
        setAdapter(savedInstanceState);
    }

    private void setAdapter(Bundle savedInstanceState) {
        activity_manger = new LocalActivityManager(this, true);
        activity_manger.dispatchCreate(savedInstanceState);

        List<View> list = new ArrayList<View>();
        Intent intent = new Intent(this, HomeActivity.class);
        View home_view = activity_manger.startActivity("HomeActivity", intent).getDecorView();
        list.add(home_view);
        Intent intent2 = new Intent(this, ArticlesActivity.class);
        View articles_view = activity_manger.startActivity("ArticlesActivity", intent2).getDecorView();
        list.add(articles_view);

        PagerAdapter adapter = new PagerAdapter(list);
        viewPager.setAdapter(adapter);
    }

    private void getBottomNavigationBar() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        // 设置底部导航栏的相关属性
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_baseline_home_24, ""))
                .addItem(new BottomNavigationItem(R.drawable.ic_baseline_article_24, ""))
                .setFirstSelectedPosition(0)
                .setMode(BottomNavigationBar.MODE_SHIFTING_NO_TITLE)
                .setActiveColor("#FFFFFF")
                .setBarBackgroundColor("#42C1A9")
                .initialise();

        // 设置底部导航栏选择时的监听器
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {
        private List<View> list;

        public PagerAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewPager viewpager = (ViewPager) container;
            View view = list.get(position);
            viewpager.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            ViewPager viewpager = (ViewPager) container;
            View view = list.get(position);
            viewpager.removeView(view);
        }
    }
}



















