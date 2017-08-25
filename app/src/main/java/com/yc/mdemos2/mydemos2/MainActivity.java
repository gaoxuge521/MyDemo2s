package com.yc.mdemos2.mydemos2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yc.mdemos2.mydemos2.choujiangpan.ChouJiangPanActivity;
import com.yc.mdemos2.mydemos2.duijiaoqie.DuiJiaoQieActivity;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.XiaZaiActivity;
import com.yc.mdemos2.mydemos2.fangshiguangwangcitylist.ShiGuangCityListActivity;
import com.yc.mdemos2.mydemos2.fangwangyelanmuguanli.ui.ColumnEditActivity;
import com.yc.mdemos2.mydemos2.fangzhenfanye.turntest;
import com.yc.mdemos2.mydemos2.fanxinlangvideo.FangXinLangActivity;
import com.yc.mdemos2.mydemos2.ganjiwangchoutixiaoguo.GanjiChoutiActivity;
import com.yc.mdemos2.mydemos2.jiazaidonghua1.JiaZaiDongHua1Activity;
import com.yc.mdemos2.mydemos2.jiazaidonghua2.JiaZaiDongHua2Activity;
import com.yc.mdemos2.mydemos2.jiazaidonghua3.JiaZaiDongHua3Activity;
import com.yc.mdemos2.mydemos2.jiazaidonghua4.JiaZaiDongHua4Activity;
import com.yc.mdemos2.mydemos2.mohuchaxundesousuokuang.MoHuSearchEditextActivity;
import com.yc.mdemos2.mydemos2.qinxiedetextview.QinXieTextViewActivity;
import com.yc.mdemos2.mydemos2.recycleviews.MyRecycleViewsActivity;
import com.yc.mdemos2.mydemos2.shijianriqixuanzeqi.ShiJianRiQiXuanZeActivity;
import com.yc.mdemos2.mydemos2.shoushimima.ShouShiMiMAActivity;
import com.yc.mdemos2.mydemos2.tanmu_recycleview.TanMuActivity;
import com.yc.mdemos2.mydemos2.texiaosousuokuang.ui.home.HomeActivity;
import com.yc.mdemos2.mydemos2.weixingcaidan.WeiXingCaiDanActivity;
import com.yc.mdemos2.mydemos2.xiala.XiaLa1Activity;
import com.yc.mdemos2.mydemos2.xuanzeshurukuang.view.XuanZeShuRuKuangActivity;
import com.yc.mdemos2.mydemos2.yaoyiyao.YaoYiYaoActivity;
import com.yc.mdemos2.mydemos2.yuanjiaodialog.AlertDialogSamples;
import com.yc.mdemos2.mydemos2.zhichigezhongkongjiandexiala.EveryViewRefreshActivity;
import com.yc.mdemos2.mydemos2.zidaijiazaidonghuadewebview.MyWebViewActivity;
import com.yc.mdemos2.mydemos2.zidongbuquan.ZiDongBuQuanActivity;
import com.yc.mdemos2.mydemos2.zuocehuachoutixiaoguo.ZuoCelaActivity;
import com.yc.mdemos2.mydemos2.zuohualizixiaoguo.RecyclerViewActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_jiazaidonghua1)
    Button btnJiazaidonghua1;
    @Bind(R.id.btn_jiazaidonghua2)
    Button btnJiazaidonghua2;
    @Bind(R.id.btn_weixingcaidan)
    Button btnWeixingcaidan;
    @Bind(R.id.btn_choujiangpan)
    Button btnChoujiangpan;
    @Bind(R.id.btn_mohuchaxun_editext)
    Button btnMohuchaxunEditext;
    @Bind(R.id.btn_evert_view_refresh)
    Button btnEvertViewRefresh;
    @Bind(R.id.btn_xuanze_edittext)
    Button btnXuanzeEdittext;
    @Bind(R.id.btn_qinxie_textview)
    Button btnQinxieTextview;
    @Bind(R.id.btn_jiazaidonghua3)
    Button btnJiazaidonghua3;
    @Bind(R.id.btn_shoushimima)
    Button btnShoushimima;
    @Bind(R.id.btn_shijianriqixuanze)
    Button btnShijianriqixuanze;
    @Bind(R.id.btn_haokandexiala)
    Button btnHaokandexiala;
    @Bind(R.id.btn_yuanjiaodialog)
    Button btnYuanjiaodialog;
    @Bind(R.id.btn_zidongbuquan)
    Button btnZidongbuquan;
    @Bind(R.id.btn_ddxiazai)
    Button btnDdxiazai;
    @Bind(R.id.btn_zuohualizixiaoguo)
    Button btnZuohualizixiaoguo;
    @Bind(R.id.btn_tanmu_recycle)
    Button btnTanmuRecycle;
    @Bind(R.id.btn_zidaijiazaidonghuadewebview)
    Button btnZidaijiazaidonghuadewebview;
    @Bind(R.id.btn_texiaosearch)
    Button btnTexiaosearch;
    @Bind(R.id.btn_ganji)
    Button btnGanji;
    @Bind(R.id.btn_yaoyiyao)
    Button btnYaoyiyao;
    @Bind(R.id.btn_zuocela)
    Button btnZuocela;
    @Bind(R.id.btn_fangzhenfanye)
    Button btnFangzhenfanye;
    @Bind(R.id.btn_fangshiguangwangcitylist)
    Button btnFangshiguangwangcitylist;
    @Bind(R.id.btn_fangwangyelanmuguanli)
    Button btnFangwangyelanmuguanli;
    @Bind(R.id.btn_fangxinlangweibo)
    Button btnFangxinlangweibo;
    @Bind(R.id.btn_jiazaidonghua4)
    Button btnJiazaidonghua4;
    @Bind(R.id.btn_duijiaoqie)
    Button btnDuijiaoqie;
    @Bind(R.id.btn_recycle_gezhongxiaoguo)
    Button btnRecycleGezhongxiaoguo;
    @Bind(R.id.btn_three_d_xiaoguo)
    Button btnThreeDXiaoguo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_three_d_xiaoguo,R.id.btn_recycle_gezhongxiaoguo, R.id.btn_duijiaoqie, R.id.btn_jiazaidonghua4, R.id.btn_fangxinlangweibo, R.id.btn_fangwangyelanmuguanli, R.id.btn_fangshiguangwangcitylist, R.id.btn_fangzhenfanye, R.id.btn_zuocela, R.id.btn_yaoyiyao, R.id.btn_ganji, R.id.btn_texiaosearch, R.id.btn_zidaijiazaidonghuadewebview, R.id.btn_tanmu_recycle, R.id.btn_zuohualizixiaoguo, R.id.btn_ddxiazai, R.id.btn_zidongbuquan, R.id.btn_yuanjiaodialog, R.id.btn_haokandexiala, R.id.btn_shijianriqixuanze, R.id.btn_shoushimima, R.id.btn_jiazaidonghua3, R.id.btn_qinxie_textview, R.id.btn_xuanze_edittext, R.id.btn_evert_view_refresh, R.id.btn_mohuchaxun_editext, R.id.btn_choujiangpan, R.id.btn_weixingcaidan, R.id.btn_jiazaidonghua1, R.id.btn_jiazaidonghua2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_jiazaidonghua1:
                startActivity(new Intent(MainActivity.this, JiaZaiDongHua1Activity.class));
                break;
            case R.id.btn_recycle_gezhongxiaoguo:
                startActivity(new Intent(MainActivity.this, MyRecycleViewsActivity.class));
                break;
            case R.id.btn_duijiaoqie:
                startActivity(new Intent(MainActivity.this, DuiJiaoQieActivity.class));
                break;
            case R.id.btn_jiazaidonghua4:
                startActivity(new Intent(MainActivity.this, JiaZaiDongHua4Activity.class));
                break;
            case R.id.btn_fangxinlangweibo:
                startActivity(new Intent(MainActivity.this, FangXinLangActivity.class));
                break;
            case R.id.btn_fangwangyelanmuguanli:
                startActivity(new Intent(MainActivity.this, ColumnEditActivity.class));
                break;
            case R.id.btn_fangshiguangwangcitylist:
                startActivity(new Intent(MainActivity.this, ShiGuangCityListActivity.class));
                break;
            case R.id.btn_zuocela:
                startActivity(new Intent(MainActivity.this, ZuoCelaActivity.class));
                break;
            case R.id.btn_yaoyiyao:
                startActivity(new Intent(MainActivity.this, YaoYiYaoActivity.class));
                break;
            case R.id.btn_tanmu_recycle:
                startActivity(new Intent(MainActivity.this, TanMuActivity.class));
                break;
            case R.id.btn_fangzhenfanye:
                startActivity(new Intent(MainActivity.this, turntest.class));
                break;
            case R.id.btn_jiazaidonghua2:
                startActivity(new Intent(MainActivity.this, JiaZaiDongHua2Activity.class));
                break;
            case R.id.btn_jiazaidonghua3:
                startActivity(new Intent(MainActivity.this, JiaZaiDongHua3Activity.class));
                break;
            case R.id.btn_weixingcaidan:
                startActivity(new Intent(MainActivity.this, WeiXingCaiDanActivity.class));
                break;
            case R.id.btn_choujiangpan:
                startActivity(new Intent(MainActivity.this, ChouJiangPanActivity.class));
                break;
            case R.id.btn_mohuchaxun_editext:
                startActivity(new Intent(MainActivity.this, MoHuSearchEditextActivity.class));
                break;
            case R.id.btn_evert_view_refresh:
                startActivity(new Intent(MainActivity.this, EveryViewRefreshActivity.class));
                break;
            case R.id.btn_xuanze_edittext:
                startActivity(new Intent(MainActivity.this, XuanZeShuRuKuangActivity.class));
                break;
            case R.id.btn_qinxie_textview:
                startActivity(new Intent(MainActivity.this, QinXieTextViewActivity.class));
                break;
            case R.id.btn_shoushimima:
                startActivity(new Intent(MainActivity.this, ShouShiMiMAActivity.class));
                break;
            case R.id.btn_shijianriqixuanze:
                startActivity(new Intent(MainActivity.this, ShiJianRiQiXuanZeActivity.class));
                break;
            case R.id.btn_haokandexiala:
                startActivity(new Intent(MainActivity.this, XiaLa1Activity.class));
                break;
            case R.id.btn_yuanjiaodialog:
                startActivity(new Intent(MainActivity.this, AlertDialogSamples.class));
                break;
            case R.id.btn_three_d_xiaoguo:
                startActivity(new Intent(MainActivity.this, com.yc.mdemos2.mydemos2.threeDxiaoguo.HomeActivity.class));
                break;
            case R.id.btn_zidongbuquan:
                startActivity(new Intent(MainActivity.this, ZiDongBuQuanActivity.class));
                break;
            case R.id.btn_ddxiazai:
                startActivity(new Intent(MainActivity.this, XiaZaiActivity.class));
                break;
            case R.id.btn_zuohualizixiaoguo:
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                break;
            case R.id.btn_zidaijiazaidonghuadewebview:
                startActivity(new Intent(MainActivity.this, MyWebViewActivity.class));
                break;
            case R.id.btn_texiaosearch:
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                break;
            case R.id.btn_ganji:
                startActivity(new Intent(MainActivity.this, GanjiChoutiActivity.class));
                break;
        }
    }

}
