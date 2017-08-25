package com.yc.mdemos2.mydemos2.jiazaidonghua2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yc.mdemos2.mydemos2.R;

public class CircleJumpActivity extends AppCompatActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CircleJumpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_jump);
    }
}
