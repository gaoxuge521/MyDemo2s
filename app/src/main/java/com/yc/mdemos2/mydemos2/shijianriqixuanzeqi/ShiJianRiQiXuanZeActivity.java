package com.yc.mdemos2.mydemos2.shijianriqixuanzeqi;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.yc.mdemos2.mydemos2.R;

import java.util.List;

import cn.aigestudio.datepicker.interfaces.OnDateSelected;
import cn.aigestudio.datepicker.utils.LogUtil;
import cn.aigestudio.datepicker.views.DatePicker;

public class ShiJianRiQiXuanZeActivity extends AppCompatActivity {
    private DatePicker mDatePicker;
    private Button btnPick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_jian_ri_qi_xuan_ze);
        mDatePicker = (DatePicker) findViewById(R.id.main_dp);
        mDatePicker.setOnDateSelected(new OnDateSelected() {
            @Override
            public void selected(List<String> date) {
                for (String s : date) {
                    LogUtil.v(s);
                }
            }
        });

        btnPick = (Button) findViewById(R.id.main_btn);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(ShiJianRiQiXuanZeActivity.this).create();
                dialog.show();

                DatePicker datePicker = new DatePicker(ShiJianRiQiXuanZeActivity.this);
                datePicker.setOnDateSelected(new OnDateSelected() {
                    @Override
                    public void selected(List<String> date) {
                        StringBuilder sb = new StringBuilder();
                        for (String s : date) {
                            sb.append(s).append("\n");
                        }
                        Toast.makeText(ShiJianRiQiXuanZeActivity.this, sb.toString(),
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                        .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(datePicker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }
}

