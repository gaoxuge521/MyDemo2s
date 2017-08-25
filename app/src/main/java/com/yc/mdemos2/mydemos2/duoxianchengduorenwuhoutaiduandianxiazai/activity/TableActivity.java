 package com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.activity;

 import android.app.TabActivity;
 import android.content.Intent;
 import android.os.Bundle;
 import android.widget.TabHost;

 import com.yc.mdemos2.mydemos2.R;
 import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.constant.AppConstant;

 public class TableActivity extends TabActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);
		createFrame();
	}
	
	/**
	 * 创建框架，2个标签
	 * 1.网络音频
	 * 2.本地下载
	 * **/
	private void createFrame()
	{
		TabHost tabHost = this.getTabHost();
	
		
		
		//start:这里创建一个本地下载的标签
		Intent localIntent = new Intent();
		localIntent.setClass(TableActivity.this, CurrentDownActivity.class);
		TabHost.TabSpec localSpec=tabHost.newTabSpec("local");
		localSpec.setIndicator(AppConstant.MainConstant.localTabName);
		localSpec.setContent(localIntent);
		tabHost.addTab(localSpec);
		//end
		//start:这里创建一个网络音频的标签
		//创建一个intent，用于跳转到标签对应的activity
		Intent networkIntent = new Intent(); 
		networkIntent.setClass(TableActivity.this, FinishActivity.class);
		TabHost.TabSpec networkSpec= tabHost.newTabSpec("network");//创建标签页
		networkSpec.setIndicator(AppConstant.MainConstant.netTabName);//设置信息显示板,也就是标签的名称
		networkSpec.setContent(networkIntent);//设置标签对应的内容
		tabHost.addTab(networkSpec);//添加标签
				//end

		////设置当前显示哪个标签 
		tabHost.setCurrentTab(0);
	}
	
	
	
}
