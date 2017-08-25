package com.yc.mdemos2.mydemos2.jiazaidonghua3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yc.mdemos2.mydemos2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.co.recruit_lifestyle.android.widget.ColoringLoadingView;

public class JiaZaiDongHua3Activity extends FragmentActivity {

    @Bind(R.id.vp)
    ViewPager vp;
    private List<Fragment> fragmentList = new ArrayList<>();

    private ColoringLoadingView.Character character[] = {ColoringLoadingView.Character.AK,ColoringLoadingView.Character.AK,ColoringLoadingView.Character.AK,ColoringLoadingView.Character.AK,ColoringLoadingView.Character.AK,ColoringLoadingView.Character.AK,ColoringLoadingView.Character.AK,ColoringLoadingView.Character.AK,ColoringLoadingView.Character.AK,ColoringLoadingView.Character.AK};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_jia_zai_dong_hua3);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {

        for(int i = 0;i< character.length;i++){

            ImgFragments fragments = new ImgFragments();
            Bundle data = new Bundle();
            data.putInt("path",i);
            fragments.setArguments(data);
            fragmentList.add(fragments);
        }


        vp.setAdapter(new MyAdapter(getSupportFragmentManager()));



//        final ColoringLoadingView loadingView = (ColoringLoadingView) findViewById(R.id.main_loading);
//        loadingView.setCharacter(ColoringLoadingView.Character.NINJA);
//        loadingView.setColoringColor(0xFFFF1744);
//
//        findViewById(R.id.main_start_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadingView.setVisibility(View.VISIBLE);
//                loadingView.startDrawAnimation();
//            }
//        });
    }


    public class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}

