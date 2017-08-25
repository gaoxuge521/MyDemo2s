package com.yc.mdemos2.mydemos2.yaoyiyao;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yc.mdemos2.mydemos2.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YaoYiYaoActivity extends AppCompatActivity {

    ShakeListenner shakeListenner;
    SensorManager sensorManager;
    @Bind(R.id.zd)
    ImageView zd;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yao_yi_yao);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //摇一摇对象
        shakeListenner = new ShakeListenner() {
            @Override
            public void shakeCrateLottery() {
                zd.setVisibility(View.VISIBLE);
                Toast.makeText(YaoYiYaoActivity.this, "你摇手机了---------", Toast.LENGTH_SHORT).show();
                zhendong();
            }


        };

        initShake();
    }

    private void zhendong() {
        /*
         * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
         * */

        long[] pattern = {100, 400, 100, 400};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1);           //重复两次上面的pattern 如果只想震动一次，index
    }

    @Override
    protected void onPause() {
        super.onPause();
        //取消注册
        sensorManager.unregisterListener(shakeListenner);
        //停止震动
        vibrator.cancel();
    }

    private void initShake() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(shakeListenner, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_FASTEST);
    }
}
