package com.yc.mdemos2.mydemos2.fangshiguangwangcitylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.yc.mdemos2.mydemos2.R;

public class ShiGuangCityListActivity extends AppCompatActivity {
    private ListView menu_city_list;
    private MenuListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_guang_city_list);
        init();
    }
    public void init()
    {
        String pyStr[] = {"A","B","C","D","X","Y","Z"};
        String companyStr[][] = {{"澳门"},{"北京","包头","保定"},{"重庆","成都","常州","长沙"},{"东京冷","东台","大丰","大连"},{"厦门","西安","香港","兴安","襄阳","湘潭","湘西"},{"扬州"},{"株洲"}};

        menu_city_list = (ListView)findViewById(R.id.menu_city_list);

        listAdapter= new MenuListAdapter(companyStr, pyStr, this);
        menu_city_list.setAdapter(listAdapter);
    }


}

