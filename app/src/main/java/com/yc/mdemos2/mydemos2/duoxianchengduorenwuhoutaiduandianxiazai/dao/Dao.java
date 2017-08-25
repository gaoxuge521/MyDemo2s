package com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.dao;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.db.DBHelper;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.DownloadInfo;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.FileState;

import java.util.ArrayList;
import java.util.List;


public class Dao
{
	private DBHelper dbHelper;
	private static final String DATABASE_NAME = "down.db";
	public static String Lock = "dblock"; 
	public static String file_Lock="fileLock";
	
	 public Dao(Context context) 
	 {
	        dbHelper = new DBHelper(context);
	 }
	 
	     /**
	     * 查看線程数据表中是否有数据
	     * @param urlstr 下载地址
	     * @return count==0 如果数据库里没有数据,返回true,如果数据库里有数据返回false
	     */
	    public boolean isHasInfors(String urlstr) 
	    {
	        SQLiteDatabase database = dbHelper.getReadableDatabase();
	        String sql = "select count(*)  from thread_tab where url=?";
	        Cursor cursor = database.rawQuery(sql, new String[] { urlstr });
	        cursor.moveToFirst();
	        //getInt方法返回第0列的值
	        int count = cursor.getInt(0);
	        cursor.close();
	        database.close();
	        return count == 0;
	    }
	 
	    /**
	     * 插入一條 線程下载的具体信息
	     * 保存和更新方法最好设置为同步
	     */
	    public  void saveInfos(List<DownloadInfo> infos, Context context)
	    {
	    	//这里也要采用事务的方法提高效率
	    	
	        synchronized (Lock) 
	        {
				SQLiteDatabase database = context.openOrCreateDatabase(	DATABASE_NAME, Context.MODE_PRIVATE, null);
				database.beginTransaction();
				try {
					for (DownloadInfo info : infos) {
						String sql = "insert into thread_tab(thread_id,start_pos, end_pos,compelete_size,url) values (?,?,?,?,?)";
						Object[] bindArgs = { info.getThreadId(),
								info.getStartPos(), info.getEndPos(),
								info.getCompeleteSize(), info.getUrl() };
						database.execSQL(sql, bindArgs);
					}
					database.setTransactionSuccessful();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					database.endTransaction();
				}
				database.close();
			}
	      
	    }
	        
	    /**
	     * 插入一条已下載好的文件记录
	     * **/
	    public void insertFileState(FileState fileState, Context context)
	    {
	    	synchronized(file_Lock)
	    	{
	    		SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
	    		database.beginTransaction();
	    		try 
	    		{
	    			//System.out.println("insert data");
					String sql="insert into localfile_tab (file_name,url,fileSize) values(?,?,?)";
					Object[] bindArgs={fileState.getFileName(),
										fileState.getUrl(),
										fileState.getFileSize(),									
										};
					database.execSQL(sql, bindArgs);
					database.setTransactionSuccessful();
				}
	    		catch (SQLException e) 
	    		{
					e.printStackTrace();
				}
	    		finally
	    		{
					database.endTransaction();
				}
				database.close();
	    	}
	    }
	    
	    /**
	     * 獲取指定路徑的線程的具体下载信息
	     * @return List<DownloadInfo> 一个下载器信息集合器,里面存放了每条线程的下载信息
	     */
	    public List<DownloadInfo> getInfos(String urlstr) 
	    {
	        List<DownloadInfo> list = new ArrayList<DownloadInfo>();
	        SQLiteDatabase database = dbHelper.getReadableDatabase();
	        String sql = "select thread_id, start_pos, end_pos,compelete_size,url from thread_tab where url=?";
	        Cursor cursor = database.rawQuery(sql, new String[] { urlstr });
	        while (cursor.moveToNext()) {
	            DownloadInfo info = new DownloadInfo(cursor.getInt(0),
	                    cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
	                    cursor.getString(4));
	            list.add(info);
	        }
	        cursor.close();
	        database.close();
	        return list;
	    }
	    
	    
	    /**
	     * 獲取已下載的文件
	     * @return List<DownloadInfo> 一个下载器信息集合器,里面存放了每条线程的下载信息
	     */
	    public List<FileState> fulfillFile ()
	    {
	        List<FileState> list = new ArrayList<FileState>();
	        SQLiteDatabase database = dbHelper.getReadableDatabase();
	        String sql = "select file_name,url,fileSize from localfile_tab ";
	        Cursor cursor = database.rawQuery(sql, null );
	        while (cursor.moveToNext()) {
	        	FileState info = new FileState(cursor.getString(0),
	        			cursor.getString(1), cursor.getInt(2));
	            list.add(info);
	        }
	        cursor.close();
	        database.close();
	        return list;
	    }
	    
	    
	    
	    
	    /**
	     * 取出數據庫存在的線程記錄
	     * @return
	     */
	    public List<String> url(){
			 List<String> list = new ArrayList<String>();
			 SQLiteDatabase database = dbHelper.getReadableDatabase();
			 String sql = "select distinct url from thread_tab ";
			 
		     Cursor cursor=database.rawQuery(sql, null);
		     while (cursor.moveToNext()) {
		     list.add(cursor.getString(0));
		     }
		     cursor.close();
		    database.close();
	    	return list;

	    }
	    

	    
	    /**
	     * 如果不存在则返回true
	     * **/
	    public boolean isHasFile(String url)
	    {
	    	SQLiteDatabase database = dbHelper.getReadableDatabase();
	        String sql = "select count(*)  from thread_tab where url=?";
	        Cursor cursor = database.rawQuery(sql, new String[] { url });
	        cursor.moveToFirst();
	        //getInt方法返回第0列的值
	        int count = cursor.getInt(0);
	        cursor.close();
	        database.close();
	        return count == 0;
	    }
	    
	    
	    
	    /**
	     * 更新数据库中的下载信息,保存和更新方法最好设置为同步
	     * @param threadId 线程号
	     * @param compeleteSize 已经下载的长度
	     * @param urlstr 下载地址
	     */
	    public void updataInfos(int threadId, int compeleteSize, String urlstr,Context context) 
	    {
	    	synchronized (Lock) 
	    	{
				//这里因为是要更新数据,所以要采用写操作,和事务的方法来提高效率
				//SQLiteDatabase database = dbHelper.getReadableDatabase();
				String sql = "update thread_tab set compelete_size=? where thread_id=? and url=?";
				Object[] bindArgs = { compeleteSize, threadId, urlstr };
				//SQLiteDatabase database = dbHelper.getWritableDatabase();
				SQLiteDatabase database = context.openOrCreateDatabase(
						DATABASE_NAME, Context.MODE_PRIVATE, null);
				database.beginTransaction();
				try {
					database.execSQL(sql, bindArgs);
					database.setTransactionSuccessful();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					database.endTransaction();
				}
				database.close();
			}
	    
	    }
	    

   
	    /**
	     * 关闭数据库
	     */
	    public void closeDb() {
	        dbHelper.close();
	    }

	    /**
	     * 刪除指定路徑的所有線程記錄,用在刪除文件的時候
	     */
	    public void delete(String url) 
	    {
	        SQLiteDatabase database = dbHelper.getReadableDatabase();
	       
	        database.execSQL("DELETE FROM thread_tab WHERE url = ? ",new Object[]{url});
	        database.close();
	    }
	    
	   
	  
	    /**
	     * 刪除單條線程的下載記錄，用在每條線程下載完成后
	     * @param path
	     * @param threadid
	     */
		public void delete(String url , int threadid){
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			db.execSQL("DELETE FROM thread_tab WHERE url = ? AND thread_id = ?",new Object[]{url,threadid});
			db.close();
		}
	    
		 /**
	     * 刪除文件信息  用在刪除文件的時候
	     *  @param musicName
	     */
	    public void deleteFileState(String fileName)
	    {
	    	SQLiteDatabase database=dbHelper.getReadableDatabase();
	    	database.delete("localfile_tab", "file_name=?",new String[]{fileName});
	    	database.close();
	    }

}
