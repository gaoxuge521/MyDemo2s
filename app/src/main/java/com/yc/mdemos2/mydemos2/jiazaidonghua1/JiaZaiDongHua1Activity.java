package com.yc.mdemos2.mydemos2.jiazaidonghua1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bcgtgjyb.huanwen.customview.mylibrary.FiveLine;
import com.bcgtgjyb.huanwen.customview.mylibrary.TaiJiButton;
import com.yc.mdemos2.mydemos2.R;

public class JiaZaiDongHua1Activity extends AppCompatActivity {
    private Button button;
    private TaiJiButton taiJiButton;
    private FiveLine fiveLine1;
    private FiveLine fiveLine2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jia_zai_dong_hua1);
        init();
    }

    private void init() {
        taiJiButton = (TaiJiButton) findViewById(R.id.taiJiButton);
        fiveLine1 = (FiveLine) findViewById(R.id.fiveLine1);
        fiveLine2 = (FiveLine) findViewById(R.id.fiveLine2);
        fiveLine2.initLine(new float[]{0,0.2f,0.4f,0.6f,0.8f});

//        taiJiButton.setVelocity(5000);
        taiJiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!taiJiButton.isLoading()) {
                    taiJiButton.startLoad();
                }
            }
        });


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taiJiButton != null && taiJiButton.isLoading()) {
                    taiJiButton.stopLoad();
                }
            }
        });
    }
}

