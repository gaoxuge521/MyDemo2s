package com.yc.mdemos2.mydemos2.xuanzeshurukuang.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yc.mdemos2.mydemos2.R;

import java.util.ArrayList;
import java.util.List;

public class XuanZeShuRuKuangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_ze_shu_ru_kuang);
        DropEditText drop1 = (DropEditText) findViewById(R.id.drop_edit);
        DropEditText drop2 = (DropEditText) findViewById(R.id.drop_edit2);

        drop1.setAdapter(new BaseAdapter() {
            private List<String> mList = new ArrayList<String>() {
                {
                    add("常用选项 one");
                    add("常用选项 two");
                    add("常用选项 three");
                    add("常用选项 four");
                    add("常用选项 five");
                    add("常用选项 six");
                    add("常用选项 7怎么拼来着？");
                }
            };

            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Object getItem(int position) {
                return mList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = new TextView(XuanZeShuRuKuangActivity.this);
                tv.setText(mList.get(position));
                return tv;
            }
        });

        drop2.setAdapter(new BaseAdapter() {
            private List<String> mList = new ArrayList<String>() {
                {
                    add("常用选项 one");
                    add("常用选项 two");
                    add("常用选项 three");
                    add("常用选项 four");
                    add("常用选项 five");
                    add("常用选项 six");
                    add("常用选项 7怎么拼来着？");
                }
            };

            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Object getItem(int position) {
                return mList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = new TextView(XuanZeShuRuKuangActivity.this);
                tv.setText(mList.get(position));
                return tv;
            }
        });
    }
}