package com.yc.mdemos2.mydemos2.zhichigezhongkongjiandexiala;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.xlf.nrl.NsRefreshLayout;
import com.yc.mdemos2.mydemos2.R;

public class EveryViewRefreshActivity extends AppCompatActivity implements
        NsRefreshLayout.NsRefreshLayoutController, NsRefreshLayout.NsRefreshLayoutListener {
    private boolean loadMoreEnable = true;
    private NsRefreshLayout refreshLayout;
    private RecyclerView rvTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_every_view_refresh);
        refreshLayout = (NsRefreshLayout) findViewById(R.id.nrl_test);
        refreshLayout.setRefreshLayoutController(this);
        refreshLayout.setRefreshLayoutListener(this);

        rvTest = (RecyclerView) findViewById(R.id.rv_test);
        TestRecyclerAdapter adapter = new TestRecyclerAdapter(this);
        rvTest.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 0, 0, "禁用上拉加载功能");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        loadMoreEnable = false;
        return true;
    }

    @Override
    public boolean isPullRefreshEnable() {
        return true;
    }

    @Override
    public boolean isPullLoadEnable() {
        return loadMoreEnable;
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishPullRefresh();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishPullLoad();
            }
        }, 1000);
    }

}
