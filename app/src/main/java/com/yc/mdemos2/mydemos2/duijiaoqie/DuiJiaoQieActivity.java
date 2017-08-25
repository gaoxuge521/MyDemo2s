package com.yc.mdemos2.mydemos2.duijiaoqie;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yc.mdemos2.mydemos2.R;

import developer.shivam.library.DiagonalView;

public class DuiJiaoQieActivity extends AppCompatActivity {
    Toolbar toolbar;
    DiagonalView diagonalView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dui_jiao_qie);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);

        diagonalView = (DiagonalView) findViewById(R.id.diagonal_view);
        diagonalView.setAngle(15);
        diagonalView.setDiagonalGravity(DiagonalView.RIGHT);
        diagonalView.setBackgroundColor(Color.TRANSPARENT);
    }
}
