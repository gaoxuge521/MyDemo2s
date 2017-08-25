package com.yc.mdemos2.mydemos2.xiala;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yc.mdemos2.mydemos2.R;

public class XiaLa1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xia_la1);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new ListViewFragment())
                    .commit();
        }
    }
}
