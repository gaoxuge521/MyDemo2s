package com.yc.mdemos2.mydemos2.tanmu_recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yc.mdemos2.mydemos2.R;

import io.xujiaji.dmlib.barrage.DanMu;

public class TanMuActivity extends AppCompatActivity {
    private RecyclerView rvBarrage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan_mu);
        rvBarrage = (RecyclerView) findViewById(R.id.rvBarrage);
        DanMu.init(rvBarrage);
//        Config config = new Config(
//                R.layout.item,
//                R.id.tvName,
//                R.id.tvMsg,
//                R.id.imgHead);
//        config.setRowNum(5);
//        DanMu.init(rvBarrage, config);
    }

    public void onAddClick(View view) {
        DanMu.call()
                .picUrl("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=150237755,4294706681&fm=116&gp=0.jpg")
                .name("我是高续铬")
                .msg("这是我发的弹幕")
                .show();
    }
}
