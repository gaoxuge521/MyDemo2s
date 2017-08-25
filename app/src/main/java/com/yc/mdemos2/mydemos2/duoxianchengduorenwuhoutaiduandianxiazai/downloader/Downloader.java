package com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.downloader;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.constant.AppConstant;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.dao.Dao;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.DownloadInfo;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.ListState;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Downloader 
{
	private String downPath;//下载路径
	private String savePath;//保存路径
	private String fileName;//文件名字
	private int threadCount;//线程数
	private Handler mHandler;
	private Dao dao;
	private Context context;
	private static final int INIT = 0;//定义三种下载的状态：0初始化状态，1正在下载状态，2暂停状态
	private static final int DOWNLOADING = 1;
	private static final int PAUSE = 2;
    
    /**
     * @param downPath 下载地址
     * @param savePath 保存地址
     * @param fileName 文件名称
     * @param threadcount 使用的线程数
     * @param context 用来创建一个DAO对象
     * **/
	public Downloader(String downPath, String savePath, String fileName,
			int threadCount, Context context,Handler mHandler) 
	{
		this.downPath = downPath;
		this.savePath = savePath;
		this.fileName = fileName;
		this.threadCount = threadCount;
		this.context = context;
		this.mHandler=mHandler;
		dao=new Dao(context);
	}
    
   
    /**
     * 利用线程开始下载数据
     */
    public void download(List<DownloadInfo> infos)
    {
    	if(infos!=null)
    	{
    		String url = null;		
    		for (DownloadInfo info : infos)
    		{
    			url = info.getUrl();
			}
    		String name = url.substring(url.lastIndexOf('/')+1);
    		int state=  ListState.state.get(name);
    		if(state == DOWNLOADING)
    		{
    			return;
    		}   	
    		state = DOWNLOADING;
    		ListState.state.put(name, state);//把状态设置为正在下载
    		for (DownloadInfo info : infos)
    		{
    			new MyThread(info.getThreadId(),info.getStartPos(),
    					info.getEndPos(),info.getCompeleteSize(),
    					info.getUrl(),this.context).start();
			}
    		
    	}
    }
    

    /**
     * MyThread是一个内部类，它继承自Thread类
     * **/
    public class MyThread extends Thread
    {
    	 private int threadId;
         private int startPos;
         private int endPos;
         private int compeleteSize;
         private String urlstr;
         private Context context;
         
         /**
          * @param threadId 线程号
          * @param startPos 线程开始下载的位置
          * @param endPos   线程结束下载的位置
          * @param compeleteSize 每个线程完成下载的长度
          * @param urlstr 下载地址
          * **/
         public MyThread(int threadId, int startPos, int endPos,
                 int compeleteSize, String urlstr,Context context) 
         {
             this.threadId = threadId;
             this.startPos = startPos;
             this.endPos = endPos;
             this.compeleteSize = compeleteSize;
             this.urlstr = urlstr;
             this.context=context;
         }


		@Override
		public void run()
		{
			
			HttpURLConnection conn=null;
			RandomAccessFile randomAccessFile = null;
			InputStream inStream = null;
			File file = new File(AppConstant.NetworkConstant.savePath,urlstr.substring(urlstr.lastIndexOf('/')+1));
			try
			{
				URL url= new URL(this.urlstr);
				conn = (HttpURLConnection)url.openConnection();
				constructConn(conn);
				System.out.println("responseCode:"+conn.getResponseCode());
				if(conn.getResponseCode()==200||conn.getResponseCode()==206)
				{
					randomAccessFile = new RandomAccessFile(file,"rwd"); 		//创建文件前应该先判断Sdcard是否可用，这里需要完善
					//这里的参数只所以要加上compeleteSize完全是为了断点续传考虑,因为很可能不是第1次下载
					randomAccessFile.seek(this.startPos+this.compeleteSize);	//这里设置线程从哪个地方开始写入数据,这里是与网上获取数据是一样的
					inStream = conn.getInputStream();
					byte buffer[]=new byte[4096];//設置每次向磁盤寫入多少個字節
					int length=0;
					
					
					while((length=inStream.read(buffer, 0, buffer.length))!=-1)
					{
						randomAccessFile.write(buffer, 0, length);
						compeleteSize+=length;//累加已经下载的长度
						// 更新数据库中的下载信息
						dao.updataInfos(threadId, compeleteSize, urlstr,this.context);
						//用消息将下载信息传给进度条，对进度条进行更新
						Message message = Message.obtain();
						message.what=1;
						message.obj=urlstr;
						message.arg1=length;						
						mHandler.sendMessage(message);//给DownloaderService发送消息
						String name = urlstr.substring(urlstr.lastIndexOf('/')+1);
						int state=  ListState.state.get(name);	
                        if (state == PAUSE) 
		                {
							print(name+"線程"+threadId+"已暫停");
							return;
		                }               
					}
					
						String name = urlstr.substring(urlstr.lastIndexOf('/')+1);
					 if((endPos+1) == (startPos + compeleteSize) || endPos == (startPos + compeleteSize)){
 						print(Integer.toString(endPos)+"--------------endPosition");
 						print(Integer.toString(startPos)+"--------------startPosition");
 						print(Integer.toString(compeleteSize)+"--------------compeleteSize");
 						print(name+"线程："+Integer.toString(threadId)+" 下载完毕");

 					}else{
 						print(Integer.toString(endPos)+"--------------endPosition");
 						print(Integer.toString(startPos)+"--------------startPosition");
 						print(Integer.toString(compeleteSize)+"--------------compeleteSize");
 						print("线程："+Integer.toString(threadId)+" 未下载完！");
 					}
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			 finally 
	         {
				 try 
				 {
					if (inStream!=null) {
						inStream.close();
						randomAccessFile.close();
					}
					conn.disconnect();
					dao.closeDb();
				 }
				 catch (Exception e) 
				 {
					e.printStackTrace();
				 }
			}

		}
         
	


		/***********************************************************************
		 * 构建请求连接时的参数 返回开始下载的位置
		 * 
		 * @throws IOException
		 **********************************************************************/
    	private void constructConn(HttpURLConnection conn) throws IOException
    	{
    		conn.setConnectTimeout(5 * 1000);//一定要设置连接超时噢。这里定为5秒
    		conn.setRequestMethod("GET");//采用GET方式提交
    		conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
    		conn.setRequestProperty("Accept-Language", "zh-CN");
    		conn.setRequestProperty("Referer", this.urlstr); 
    		conn.setRequestProperty("Charset", "UTF-8");
    		int startPosition=this.startPos + this.compeleteSize;
    		 // 设置范围，格式为Range：bytes x-y;
            //这行代码就是实现多线程的关键,Range字段允许用户设置下载的开始地址和结束地址,当然range还有很多其他的用法
    		conn.setRequestProperty("Range", "bytes=" + startPosition + "-"+ this.endPos);//设置获取实体数据的范围
    		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
    		conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();
    	}
    }
    /**
     * 輸出打印信息
     * @param msg
     */
	private void print(String msg) {
		Log.d("Downloader", msg);
		
	}
}
