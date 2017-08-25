package com.yc.mdemos2.mydemos2.texiaosousuokuang.ui.home;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.yc.mdemos2.mydemos2.R;

public class HomeActivity extends FragmentActivity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setTheme(R.style.ActionSearchBar);
        ActionSearchBar.createBuilder(R.id.test, this, getSupportFragmentManager())
                .setListener(new ActionSearchBar.ActionSearchBarListener() {
                    @Override
                    public void onEnter(String type, String text) {
                        System.out.println("xxxxxx" + type + " " + text);
                    }
                }).show();
    }
}
