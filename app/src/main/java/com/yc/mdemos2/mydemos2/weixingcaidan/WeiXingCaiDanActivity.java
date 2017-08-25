package com.yc.mdemos2.mydemos2.weixingcaidan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.yc.mdemos2.mydemos2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeiXingCaiDanActivity extends AppCompatActivity {

    @Bind(R.id.menu)
    SatelliteMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_xing_cai_dan);
        ButterKnife.bind(this);
        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(6, R.drawable.ic_1));
        items.add(new SatelliteMenuItem(5, R.drawable.ic_3));
        items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
        items.add(new SatelliteMenuItem(3, R.drawable.ic_5));
        items.add(new SatelliteMenuItem(2, R.drawable.ic_6));
        items.add(new SatelliteMenuItem(1, R.drawable.ic_2));
        menu.addItems(items);

        menu.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {

            public void eventOccured(int id) {
                Log.i("sat", "Clicked on " + id);
                Toast.makeText(WeiXingCaiDanActivity.this,"Clicked on"+id,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
