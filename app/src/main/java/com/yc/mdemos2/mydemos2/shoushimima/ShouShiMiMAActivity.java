package com.yc.mdemos2.mydemos2.shoushimima;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;
import com.yc.mdemos2.mydemos2.R;

public class ShouShiMiMAActivity extends AppCompatActivity {
    private Lock9View lock9View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_shi_mi_ma);
        lock9View = (Lock9View) findViewById(R.id.lock_9_view);
        lock9View.setCallBack(new Lock9View.CallBack() {

            @Override
            public void onFinish(String password) {
                Toast.makeText(ShouShiMiMAActivity.this, password, Toast.LENGTH_SHORT).show();
            }

        });
    }


}

