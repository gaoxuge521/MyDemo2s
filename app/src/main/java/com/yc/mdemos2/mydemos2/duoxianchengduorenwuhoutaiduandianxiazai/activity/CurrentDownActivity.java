package com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.activity;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yc.mdemos2.mydemos2.R;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.adapter.CurrentDownAdapter;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.constant.AppConstant;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.dao.Dao;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.DownloadInfo;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.FileState;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.service.DownloadService;

import java.util.ArrayList;
import java.util.List;

public class CurrentDownActivity extends Activity 
{
	private Dao dao=null;//用来与数据库交互
	private ListView listView=null;
	public List<FileState> fileList=null;//用于存放要显示的列表
	private CurrentDownAdapter adapter;//自定义adapter
	private UpdateReceiver myReceiver;//广播接收器
	private List<DownloadInfo> infos;// 存放下载信息类的集合
    private Button button;
 	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		dao=new Dao(this);
		myReceiver = new UpdateReceiver(this);
		//对广播进行注册
		myReceiver.registerAction(AppConstant.LocalActivityConstant.update_action);
	}
	
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		//从数据库的localdown_info表中获取数据
		fileList = new ArrayList<FileState>();
		List<String> urls = dao.url();//把數據庫存在的數據取出來		
		for (String url : urls) {
			infos=dao.getInfos(url);//根據路徑得到具體下載信息
			int fileSize=0;
			int completeSize=0;
			for (DownloadInfo info : infos)
			{
				completeSize+=info.getCompeleteSize();//把每条线程下载的长度累加起来,得到整个文件的下载长度
				fileSize+=info.getEndPos()-info.getStartPos()+1;//计算出文件的大小,用每条线程的结束位置减去开始下载的位置,等于每条线程要下载的长度，然后累加
			}
			String musicName = url.substring(url.lastIndexOf('/')+1);
			FileState fileState = new FileState(musicName,url,completeSize,fileSize);
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
		adapter = new CurrentDownAdapter(this,fileList,dao);
		listView.setAdapter(adapter);
		//点击item可以暂停下载，开始下载
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				TextView text=(TextView)view.findViewById(R.id.local_filename);
				String fileName=text.getText().toString();
				Intent intent = new Intent();
				intent.setClass(CurrentDownActivity.this, DownloadService.class);
				intent.putExtra("fileName", fileName);
				intent.putExtra("flag","setState");//标志着数据从localdownactivity传送
				CurrentDownActivity.this.startService(intent);//这里启动service
				
			}
			
		});
	}
	
	@Override
	protected void onDestroy() 
	{	
		super.onDestroy();
	}
	
	/**
	 * 廣播接收器
	 * @author Administrator
	 *
	 */
	class UpdateReceiver extends BroadcastReceiver
	{
		private Context context;
		public UpdateReceiver(Context context)
		{
			this.context=context;
		}
		
		public void registerAction(String action)
		{
			IntentFilter intentFilter=new IntentFilter();
			intentFilter.addAction(action);
			registerReceiver(this, intentFilter);
		}
		
		@Override
		public void onReceive(Context context, Intent intent)
		{
			//接收来自DownloadService传送过来的数据,并且更新进度条
			if(intent.getAction().equals(AppConstant.LocalActivityConstant.update_action))
			{
				String url=intent.getStringExtra("url");
				int completeSize = intent.getIntExtra("completeSize", 0);
				for(int i=0;i<fileList.size();i++)
				{
					FileState fileState=fileList.get(i);
					if(fileState.getUrl().equals(url))
					{
						fileState.setCompleteSize(completeSize);
						fileList.set(i, fileState);//更新list中的数据
						break;
					}
				}
				adapter.setList(fileList);//更新完list的数据要把它重新传入adapter，这样才能更新
				adapter.notifyDataSetChanged();
			}
		}
	}

}
