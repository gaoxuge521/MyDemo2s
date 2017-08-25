package com.yc.mdemos2.mydemos2.jiazaidonghua3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yc.mdemos2.mydemos2.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.co.recruit_lifestyle.android.widget.ColoringLoadingView;

/**
 * Created by Administrator on 2016/12/26.
 */
public class ImgFragments extends Fragment {
    @Bind(R.id.main_loading)
    ColoringLoadingView mainLoading;
    @Bind(R.id.main_start_button)
    Button mainStartButton;
    private ColoringLoadingView.Character character[] = {ColoringLoadingView.Character.AK, ColoringLoadingView.Character.BUTTERFLY, ColoringLoadingView.Character.CAT, ColoringLoadingView.Character.CUCUMBER, ColoringLoadingView.Character.DOGEZA, ColoringLoadingView.Character.HAIR_STYLE, ColoringLoadingView.Character.NINJA, ColoringLoadingView.Character.NINJA_STAR, ColoringLoadingView.Character.STORM, ColoringLoadingView.Character.TOOTH};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_img_loading, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int path = getArguments().getInt("path");
        Log.d("sss", "onActivityCreated: " + path);
        mainLoading.setCharacter(character[path]);
        mainLoading.setColoringColor(0xFFFF1744);


        mainStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLoading.setVisibility(View.VISIBLE);
                mainLoading.startDrawAnimation();
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
