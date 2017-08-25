package com.yc.mdemos2.mydemos2.zidaijiazaidonghuadewebview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yc.mdemos2.mydemos2.MainActivity;
import com.yc.mdemos2.mydemos2.R;

public class MyWebViewActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_view);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(MyWebViewActivity.this,Activity_Circle.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(MyWebViewActivity.this,Activity_Horizontal.class));
            }
        });

    }

}

