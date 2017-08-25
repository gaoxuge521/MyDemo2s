package com.yc.mdemos2.mydemos2.fangshiguangwangcitylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yc.mdemos2.mydemos2.R;

public class MenuListAdapter extends BaseAdapter
{
		private String companyStr[][];
		private String pyStr[];
		private Context mContext;

		public MenuListAdapter(String companyStr[][], String pyStr[], Context mContext) 
		{
			super();
			this.pyStr = pyStr;
			this.companyStr = companyStr;
			this.mContext = mContext;
		}

		@Override
		public int getCount() 
		{
			if (pyStr == null) 
			{
				return 0;
			} 
			else 
			{
				return this.pyStr.length;
			}
		}

		@Override
		public Object getItem(int position) 
		{
			if (pyStr == null) 
			{
				return null;
			} 
			else 
			{
				return this.pyStr[position];
			}
		}

		@Override
		public long getItemId(int position) 
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			ViewHolder holder = null;
			if (convertView == null) 
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(this.mContext).inflate(R.layout.menu_list_item, null, false);
				holder.textView = (TextView) convertView.findViewById(R.id.menu_city_py_text);
				holder.gridView = (MyGridView) convertView.findViewById(R.id.listview_item_gridview);
				convertView.setTag(holder);
			} 
			else 
			{
				holder = (ViewHolder) convertView.getTag();
			}

			if (this.pyStr != null) 
			{
				if (holder.textView != null) 
				{
					holder.textView.setText(pyStr[position]);
				}
				if (holder.gridView != null) 
				{
					MenuGridAdapter gridViewAdapter = new MenuGridAdapter(mContext,companyStr[position]);
					holder.gridView.setAdapter(gridViewAdapter);
				}
			}
			return convertView;
		}

		private class ViewHolder 
		{
			TextView textView;
			MyGridView gridView;
		}
}
