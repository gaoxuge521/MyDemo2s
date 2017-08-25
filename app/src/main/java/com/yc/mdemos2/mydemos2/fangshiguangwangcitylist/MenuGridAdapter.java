package com.yc.mdemos2.mydemos2.fangshiguangwangcitylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.yc.mdemos2.mydemos2.R;

public class MenuGridAdapter extends BaseAdapter
{
		private Context mContext;
		private String companyStr[];
		
		public MenuGridAdapter(Context mContext, String companyStr[])
		{
				super();
				this.mContext = mContext;
				this.companyStr = companyStr;
		}
		
		@Override
		public int getCount()
		{
				if (companyStr == null)
				{
						return 0;
				} else
				{
						return this.companyStr.length;
				}
		}
		
		@Override
		public Object getItem(int position)
		{
				if (companyStr == null)
				{
						return null;
				} else
				{
						return this.companyStr[position];
				}
		}
		
		@Override
		public long getItemId(int position)
		{
				return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
				ViewHolder holder = null;
				if (convertView == null)
				{
						holder = new ViewHolder();
						convertView = LayoutInflater.from(this.mContext).inflate(R.layout.menu_grid_item, null,false);
						holder.button = (Button) convertView.findViewById(R.id.gridview_item_button);
						convertView.setTag(holder);
						
				} else
				{
						holder = (ViewHolder) convertView.getTag();
				}
				
				if (this.companyStr != null)
				{
						if (holder.button != null)
						{
								holder.button.setText(companyStr[position]);
								holder.button.setOnClickListener(new OnClickListener()
								{
										@Override
										public void onClick(View v)
										{
												Toast.makeText(mContext, companyStr[position], Toast.LENGTH_SHORT).show();
										}
								});
						}
				}
				return convertView;
		}
		
		private class ViewHolder
		{
				Button button;
		}
}