package com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yc.mdemos2.mydemos2.R;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.constant.AppConstant;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.dao.Dao;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.FileState;

import java.io.File;
import java.util.List;


public class FinishAdapter extends BaseAdapter 
{
	private List<FileState> list=null;
	private LayoutInflater inflater=null;
	private Dao dao=null;
	private Context context;
	
	public FinishAdapter(Context context,List<FileState> list,Dao dao)
	{
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list=list;
		this.dao=dao;
		this.context=context;
	}
	
    class ViewHolder
	{
		public TextView filename;//文件名称
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
			convertView = inflater.inflate(R.layout.finish_item,null);
			holder=new ViewHolder();
			holder.filename = (TextView) convertView.findViewById(R.id.local_filename);
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
				holder.filename.setText(fileState.getFileName());
				holder.delete.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View V)
					{
						dao.deleteFileState(name);
						list.remove(file_position);//remove掉List中相應數據
						//刪除本幣文件
						File file=new File(AppConstant.NetworkConstant.savePath+name);
						if(file.delete())
						{
							Toast.makeText(context, AppConstant.AdapterConstant.delete, Toast.LENGTH_SHORT).show(); 
						}
						notifyDataSetChanged();//刷新Listview
					}
						
				});

		return convertView;
	}

	public List<FileState> getList() {
		return list;
	}

	public void setList(List<FileState> list) {
		this.list = list;
	}
}
