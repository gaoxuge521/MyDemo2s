package com.yc.mdemos2.mydemos2.zuocehuachoutixiaoguo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yc.mdemos2.mydemos2.R;

public class ZuoCelaActivity extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuo_cela);
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(btnClick);


    }

    View.OnClickListener btnClick = new View.OnClickListener() {

        public void onClick(View v) {
            Log.i("","sjglsjglsjgl................click");

            Toast.makeText(ZuoCelaActivity.this,"button clicked", Toast.LENGTH_SHORT).show();
        }
    };


}

