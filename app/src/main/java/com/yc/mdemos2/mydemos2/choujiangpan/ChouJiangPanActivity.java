package com.yc.mdemos2.mydemos2.choujiangpan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yc.mdemos2.mydemos2.R;

public class ChouJiangPanActivity extends AppCompatActivity {
    MyLotteryWheel lotterywheel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_jiang_pan);

        //控件的使用
        lotterywheel = (MyLotteryWheel) this.findViewById(R.id.lotterywheel);
        lotterywheel.setOnSelectListener(new MyLotteryWheel.SelectListener() {
            @Override
            public void onSelect(String str) {
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });
        //控件的使用
    }
}
