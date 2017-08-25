package com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper
{
	
	private static String DATABASE_NAME="down.db";
	private static int version=2;
	
	public DBHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, version);
	}

	  
    /**
     * 在down.db数据库下创建一个download_info表存储下载信息
     * 创建一个localdown_info表存储本地下载信息
     */
	@Override
	public void onCreate(SQLiteDatabase db)
	{	
		 db.execSQL("create table thread_tab(_id integer PRIMARY KEY AUTOINCREMENT, thread_id integer, "
	                + "start_pos integer, end_pos integer, compelete_size integer,url varchar(50))");
		 db.execSQL("create table localfile_tab(_id integer PRIMARY KEY AUTOINCREMENT,file_name varchar(30),url varchar(50),fileSize integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		String sql="drop table if exists thread_tab";
		String sqlOne="drop table if exists localfile_tab";
		db.execSQL(sql);
		db.execSQL(sqlOne);
		onCreate(db);
	}
	
}
