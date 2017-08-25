package com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yc.mdemos2.mydemos2.R;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.adapter.FinishAdapter;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.constant.AppConstant;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.dao.Dao;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.FileState;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.tool.CallOtherOpeanFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FinishActivity extends Activity 
{
	private Dao dao=null;//用来与数据库交互
	private ListView listView=null;
	public List<FileState> fileList=null;//用于存放要显示的列表
	private FinishAdapter adapter;//自定义adapter
	private Button button;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		dao=new Dao(this);

	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		fileList = new ArrayList<FileState>();
		List<FileState> fulfillFiles = dao.fulfillFile();//把數據庫存在的數據取出來
		for (FileState fulfillFile : fulfillFiles) {
			FileState fileState = new FileState(fulfillFile.getFileName(),fulfillFile.getUrl(),fulfillFile.getFileSize());
			fileList.add(fileState);
		}
		
		initUi();
	}
	
	/**
	 * 初始化UI 
	 * **/
	private void initUi()
	{
		listView = (ListView)this.findViewById(R.id.listview);
		adapter = new FinishAdapter(this,fileList,dao);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			//點擊可以打開下載的文件
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				TextView text=(TextView)view.findViewById(R.id.local_filename);
				String fileName=text.getText().toString();
				File file = new File(AppConstant.NetworkConstant.savePath + fileName);
				CallOtherOpeanFile callOtherOpeanFile = new CallOtherOpeanFile();
				callOtherOpeanFile.openFile(FinishActivity.this,file);
			}
			
		});
	}
	
	@Override
	protected void onDestroy() 
	{	
		super.onDestroy();
	}
	

}
