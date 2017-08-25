package com.yc.mdemos2.mydemos2.recycleviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yc.mdemos2.mydemos2.R;
import com.yc.mdemos2.mydemos2.recycleviews.activity.AllMenuActivity;
import com.yc.mdemos2.mydemos2.recycleviews.activity.DefineActivity;
import com.yc.mdemos2.mydemos2.recycleviews.activity.DragSwipeFlagsActivity;
import com.yc.mdemos2.mydemos2.recycleviews.activity.GridDragMenuActivity;
import com.yc.mdemos2.mydemos2.recycleviews.activity.ListDragMenuActivity;
import com.yc.mdemos2.mydemos2.recycleviews.activity.ListDragSwipeActivity;
import com.yc.mdemos2.mydemos2.recycleviews.activity.RefreshLoadMoreActivity;
import com.yc.mdemos2.mydemos2.recycleviews.activity.VerticalMenuActivity;
import com.yc.mdemos2.mydemos2.recycleviews.activity.ViewPagerMenuActivity;
import com.yc.mdemos2.mydemos2.recycleviews.activity.ViewTypeMenuActivity;
import com.yc.mdemos2.mydemos2.recycleviews.adapter.MainItemAdapter;
import com.yc.mdemos2.mydemos2.recycleviews.listener.OnItemClickListener;
import com.yc.mdemos2.mydemos2.recycleviews.view.ListViewDecoration;

import java.util.Arrays;
import java.util.List;

public class MyRecycleViewsActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView recyclerView;
    private List<String> titles;
    private List<String> descriptions;
    private MainItemAdapter mMainItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycle_views);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new ListViewDecoration());

        titles = Arrays.asList(getResources().getStringArray(R.array.main_item));
        descriptions = Arrays.asList(getResources().getStringArray(R.array.main_item_des));
        mMainItemAdapter = new MainItemAdapter(titles, descriptions);
        mMainItemAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mMainItemAdapter);
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, AllMenuActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, ViewTypeMenuActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, ViewPagerMenuActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, RefreshLoadMoreActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, ListDragMenuActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, GridDragMenuActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, ListDragSwipeActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, DragSwipeFlagsActivity.class));
                break;
            case 8:
                startActivity(new Intent(this, VerticalMenuActivity.class));
                break;
            case 9:
                startActivity(new Intent(this, DefineActivity.class));
                break;
        }
    }

}
