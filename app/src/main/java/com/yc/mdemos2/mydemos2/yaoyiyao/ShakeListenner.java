package com.yc.mdemos2.mydemos2.yaoyiyao;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by gateway on 2016/6/14.
 * 摇一摇功能
 */
public abstract class ShakeListenner implements SensorEventListener {

    private int duration = 100;//采样时间间隔
    private long laseTime;//上次摇动的时间
    private float lastPointX;//上一个点的x轴的加速度信息
    private float lastPointY;
    private float lastPointZ;
    private float shake; //单词增量
    private float allShake; // 总的曾量
    private float switchValue = 100; //判断是否摇晃手机的最大值

    public ShakeListenner() {
        init();
    }

    //初始化数据
    private void init() {
        laseTime = 0;
        lastPointX = 0;
        lastPointY = 0;
        lastPointZ = 0;
        shake = 0;
        allShake = 0;
    }

    /**
     * 系统会调用onSensorChanged()方法，它提供了一个SensorEvent对象。
     * SensorEvent对象包含了有关新的传感器数据的信息，
     * 包括：数据的精度、产生数据的传感器、产生数据时的时间戳、以及传感器记录的新的数据。
     * @param event
     */

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (laseTime == 0) {
            //当前时间 的毫秒数
            laseTime = System.currentTimeMillis();
            //开始的坐标
            lastPointX = event.values[SensorManager.DATA_X];
            lastPointY = event.values[SensorManager.DATA_Y];
            lastPointZ = event.values[SensorManager.DATA_Z];
        } else {
            long currentTimeMillis = System.currentTimeMillis();

            if (currentTimeMillis - laseTime > duration) {


                //第二个点的坐标
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];

                //每次传入的数据都存在微小的变动
                float dx = Math.abs(x - lastPointX);
                float dy = Math.abs(y - lastPointY);
                float dz = Math.abs(z - lastPointZ);

                //太小的变动就h忽略
                if (dx < 1) {
                    dx = 0;
                }
                if (dy < 1) {
                    dy = 0;
                }
                if (dz < 1) {
                    dz = 0;
                }

                //获取单次的变动值（单词曾量）
                shake = dx + dy + dz;
                //如果单词增量等于0，说明还处于静止状态
                if (shake == 0) {
                    //再次初始化数据
                    init();
                }

                //总增量
                allShake += shake;

                //如果总增量大于预设的值
                //说明是在摇晃手机
                //机选一注彩票
                if (allShake > switchValue) {
                    //抽象方法机选彩票
                    shakeCrateLottery();
                    //初始化数据
                    init();
                } else {
                    laseTime = System.currentTimeMillis();
                    lastPointX = event.values[SensorManager.DATA_X];
                    lastPointY = event.values[SensorManager.DATA_Y];
                    lastPointZ = event.values[SensorManager.DATA_Z];
                }
            }
        }
    }

    public abstract void shakeCrateLottery();

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}