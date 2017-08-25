package com.yc.mdemos2.mydemos2.jiazaidonghua4;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yc.mdemos2.mydemos2.R;
import com.yc.mdemos2.mydemos2.jiazaidonghua4.DampingView.DampingViewPager;

import java.util.ArrayList;
import java.util.List;

public class JiaZaiDongHua4Activity extends AppCompatActivity {
    private List<Fragment> fragments;
    private String[] tabs = {"第一","第二","第三"};
    private DampingViewPager dampingViewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jia_zai_dong_hua4);
        dampingViewPager = (DampingViewPager) findViewById(R.id.damp_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("阻尼效果");
        toolbar.setSubtitle("ZRZn");
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fragments = new ArrayList<>();
        fragments.add(TabFragment.newInstance("第一页"));
        fragments.add(TabFragment.newInstance("第二页"));
        fragments.add(TabFragment.newInstance("第三页"));

        dampingViewPager.setpagerCount(fragments.size());
        dampingViewPager.setOffscreenPageLimit(fragments.size());
        dampingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dampingViewPager.setCurrentIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        dampingViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });
        tabLayout.setupWithViewPager(dampingViewPager);
    }
}

