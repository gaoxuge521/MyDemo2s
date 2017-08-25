package com.yc.mdemos2.mydemos2.fangwangyelanmuguanli.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.yc.mdemos2.mydemos2.R;
import com.yc.mdemos2.mydemos2.fangwangyelanmuguanli.common.entity.TitleEntity;
import com.yc.mdemos2.mydemos2.fangwangyelanmuguanli.common.util.FileTools;
import com.yc.mdemos2.mydemos2.fangwangyelanmuguanli.ui.adapter.DragDropGridAdapter;
import com.yc.mdemos2.mydemos2.fangwangyelanmuguanli.ui.adapter.ServiceColumnAdapter;
import com.yc.mdemos2.mydemos2.fangwangyelanmuguanli.ui.custom.pdgrid.PagedDragDropGrid;

import java.util.ArrayList;
import java.util.Collections;

//import cn.column.app.common.entity.TitleEntity;
//import cn.column.app.common.util.FileTools;
//import cn.column.app.ui.adapter.DragDropGridAdapter;
//import cn.column.app.ui.adapter.ServiceColumnAdapter;
//import cn.column.app.ui.custom.pdgrid.PagedDragDropGrid;

public class ColumnEditActivity extends Activity{

	GridView gridView;
	ArrayList<TitleEntity> listEntity1;
	ArrayList<TitleEntity> listEntity2;
	PagedDragDropGrid dgvColumn;
	DragDropGridAdapter adapter1;
	ServiceColumnAdapter adapter2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.column_edit_layout);
		initView();
	}
	
	
	public void initView()
	{
		listEntity1=new ArrayList<TitleEntity>();
		listEntity2=new ArrayList<TitleEntity>();
		dgvColumn=(PagedDragDropGrid)findViewById(R.id.column_edit_grid_gv);
		gridView=(GridView)findViewById(R.id.column_edit_gv_gvcolumn);
		initServiceColumn();
		initColumn();
	}
	
	
	private void initColumn()
	{
		String json="";
		try {
			json= FileTools.readTxtFile(this.getAssets().open("column1.json"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("column", json);
		TitleEntity entity=new TitleEntity();
		listEntity1=new ArrayList<TitleEntity>();
		try {
			listEntity1=entity.getListEntity(json);
			Collections.sort(listEntity1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter1=new DragDropGridAdapter(this,dgvColumn,listEntity1,adapter2);
		dgvColumn.setAdapter(adapter1);
	}
	
	
	private void initServiceColumn()
	{
		String json="";
		try {
			json=FileTools.readTxtFile(this.getAssets().open("column2.json"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("column", json);
		TitleEntity entity=new TitleEntity();
		listEntity2=new ArrayList<TitleEntity>();
		try {
			listEntity2=entity.getListEntity(json);
			Collections.sort(listEntity2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter2=new ServiceColumnAdapter(this,this,listEntity2);
		gridView.setAdapter(adapter2);
		gridView.setOnItemClickListener(onItemClickListener);
	}
	
	private OnItemClickListener onItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			int index=Integer.parseInt(arg1.findViewById(R.id.column_tv_id).getTag().toString());
			listEntity1.add(listEntity2.get(index));
			listEntity2.remove(index);
			adapter2.notifyDataSetChanged();
			dgvColumn.notifyDataSetChanged();
		}
	};
	
	
}
