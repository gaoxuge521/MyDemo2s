package com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yc.mdemos2.mydemos2.R;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.constant.AppConstant;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.dao.Dao;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.FileState;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.service.DownloadService;

import java.io.File;
import java.util.List;


public class CurrentDownAdapter extends BaseAdapter 
{
	private List<FileState> list=null;
	private LayoutInflater inflater=null;
	private Dao dao=null;
	private Context context;
	
	public CurrentDownAdapter(Context context,List<FileState> list,Dao dao)
	{
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list=list;
		this.dao=dao;
		this.context=context;
	}
	
	
	
    class ViewHolder
	{
    	private CheckBox checkBox;//复选框
		public TextView filename;//文件名称
		public ProgressBar progressBar;//进度条
		public TextView filesize;//文件大小
		public TextView percent;//百分比
		public Button delete;//删除按鈕
	}
	
	
	/**
	 * 通过getCount()方法知道有多少个Item需要展示
	 * **/
	public int getCount()
	{
		return list.size();
	}

	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return list.get(position);
	}

	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		final int file_position=position;
		if(convertView==null)
		{
			convertView = inflater.inflate(R.layout.current_item,null);
			holder=new ViewHolder();
			holder.filename = (TextView) convertView.findViewById(R.id.local_filename);
			holder.progressBar=(ProgressBar)convertView.findViewById(R.id.down_progressBar);
			holder.filesize = (TextView)convertView.findViewById(R.id.fileSize);
			holder.percent = (TextView) convertView.findViewById(R.id.percent_text);
			holder.delete = (Button) convertView.findViewById(R.id.local_delete);
		
			convertView.setTag(holder);
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}
				//从列表中得到要显示的数据
				FileState fileState= list.get(position);
				final String name= fileState.getFileName();
				int completeSize=fileState.getCompleteSize();
				int fileSize=fileState.getFileSize();
				float num=(float)completeSize/(float)fileSize;
				int result=(int)(num*100);
				holder.filename.setText(fileState.getFileName());
				holder.progressBar.setVisibility(ProgressBar.VISIBLE);
				holder.progressBar.setProgress(result);
				holder.percent.setText(result+"%");
				double  size = (double)fileSize/(double)1048576;   
				String strd = String.valueOf(size);
				String filesize=strd.substring(0, strd.indexOf(".") + 3); 	
				holder.filesize.setText(filesize+"MB");
				
				holder.delete.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View V)
					{
						Intent intent = new Intent();
						intent.putExtra("fileName", name);
						intent.putExtra("flag", "delete");
						intent.setClass(context,DownloadService.class);
						context.startService(intent);
						list.remove(file_position);//remove掉List中相應數據
						//刪除本地文件
						File file=new File(AppConstant.NetworkConstant.savePath+name);
						if(file.delete())
						{
							Toast.makeText(context, AppConstant.AdapterConstant.delete, Toast.LENGTH_SHORT).show(); 
						}
						notifyDataSetChanged();
					}
						
				});
//				当文件下载完成
				if(fileState.getCompleteSize()==fileState.getFileSize()||fileState.getCompleteSize()==fileState.getFileSize()+1
						||fileState.getCompleteSize()+1==fileState.getFileSize()) 
				{
					holder.progressBar.setVisibility(ProgressBar.INVISIBLE);
					holder.percent.setText(AppConstant.AdapterConstant.down_over);
					holder.delete.setFocusable(true); //把Button的Focusable屬性設成true，讓Listview的這一行失去點擊事件，因為下載完成后再點擊LIstview的話又會重新去下載
//					
					//理想狀態是使用以下代碼，當某條記錄下載完成后，把list裏面對應的數據remove掉，然後使用notifyDataSetChanged（）方法刷新Adapter
					//界面看到的效果就是某條記錄下載完成后馬上不見掉
//					list.remove(file_position);//remove掉List中相應數據
//					LocalDownAdapter.this.notifyDataSetChanged();
//					問題是這裡爲什麽不能刷新??/求解！！
					
				}
		
		return convertView;
	}

	public List<FileState> getList() {
		return list;
	}

	public void setList(List<FileState> list) {
		this.list = list;
	}
}
