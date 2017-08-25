package com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.constant.AppConstant;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.dao.Dao;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.downloader.Downloader;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.DownloadInfo;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.FileState;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.ListState;
import com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.entity.LoadInfo;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DownloadService extends Service 
{
	public static Map<String,Downloader> downloaders=new HashMap<String, Downloader>();
	private Downloader downloader;
	public Dao dao=null;
	private int fileSize;//文件大小
	private int range; //每个线程应该下载的长度
	private List<DownloadInfo> infos;// 存放下载信息类的集合
	private int threadCount=3;//定义线程数
	private static final int INIT = 0;//定义三种下载的状态：0初始化状态，1正在下载状态，2暂停状态
	private static final int DOWNLOADING = 1;
	private static final int PAUSE = 2;
	private Handler mHandler=new Handler()
	{

		/**
		 * 接收Download中每个线程传输过来的数据
		 * **/
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==1)
			{
				String url = (String)msg.obj;
				int length = msg.arg1;
				
				int completeSize= ListState.completeSizes.get(url);
				int fileSize=ListState.fileSizes.get(url);
				completeSize+=length;
				String fileName = url.substring(url.lastIndexOf('/')+1);
				ListState.completeSizes.put(url, completeSize);
				if(completeSize==fileSize||completeSize==fileSize+1||completeSize+1==fileSize)
				{
					dao.delete(url);
					print("下載完成---------刪除"+fileName+"的所有線程下載記錄");
					//下載完成后向數據庫添加一條文件信息，用來查看下載完成的文件
					ListState.state.put(fileName, INIT);
					FileState fileState = new FileState(fileName,url,fileSize);
					dao.insertFileState(fileState,getApplicationContext());//在localdown_info表中插入一条下载数据
					print("下載完成---------插入一條文件信息"+fileName);
					Toast.makeText(getApplicationContext(), fileName+ AppConstant.AdapterConstant.down_over, Toast.LENGTH_SHORT).show();

				}
				//發送廣播
				Intent intent = new Intent();
				intent.setAction(AppConstant.LocalActivityConstant.update_action);
				intent.putExtra("completeSize", completeSize);
				intent.putExtra("url", url);
				DownloadService.this.sendBroadcast(intent);
			}
		}

		
	};
	


	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}	
	@Override
	public void onCreate()
	{
		super.onCreate();
		dao=new Dao(this);
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String fileName=intent.getStringExtra("fileName");
		String flag=intent.getStringExtra("flag");//標識
		//根據不同標識進行不同操作
		if(flag.equals("startDownload"))
		{
			if(ListState.state.get(fileName) == null){
				
				ListState.state.put(fileName, INIT);
			}
			
			startDownload(fileName);
		}
		if(flag.equals("setState"))
		{
				
				setState(fileName);
		}
		if(flag.equals("delete"))
		{
			
			String url=AppConstant.NetworkConstant.downPath+fileName;
			deleteData(url);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	public void startDownload(String fileName)
	{
		String downPath=AppConstant.NetworkConstant.downPath+fileName;//下载地址
		String savePath=AppConstant.NetworkConstant.savePath;//保存地址
		if(!dao.isHasFile(downPath))
		{
			Toast.makeText(getApplicationContext(), "文件已经存在下載列表！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//LoadInfo是一个实体类,里面封装了一些下载所需要的信息,每个loadinfo对应1个下载器
        LoadInfo loadInfo =getDownloaderInfors(downPath);
        if(loadInfo.fileSize<=0){
        	Toast.makeText(getApplicationContext(), "無法獲取下載文件", Toast.LENGTH_SHORT).show(); 
        	return;
        };
        //插入一條下載記錄
		dao.saveInfos(infos, DownloadService.this);        
		downloader = downloaders.get(downPath);
		if(downloader==null)
		{
			ListState.fileSizes.put(downPath, 0);
			ListState.completeSizes.put(downPath, 0);
			downloader = new Downloader(downPath,savePath,fileName,threadCount,this,mHandler);
			downloaders.put(downPath, downloader);//创建完一个新的下载器,必须把它加入到下载器集合里去
		}
       
		ListState.completeSizes.put(downPath, loadInfo.getComplete());
		ListState.fileSizes.put(downPath, loadInfo.getFileSize());
		 // 调用方法开始下载
        downloader.download(infos);
	}
	
	
	
	
	 /**
     * 得到downloader里的信息
     * 首先进行判断是否是第一次下载，如果是第一次就要进行初始化，并将下载器的信息保存到数据库中
     * 如果不是第一次下载，那就要从数据库中读出之前下载的信息（起始位置，结束为止，文件大小等），并将下载信息返回给下载器
	 * @return 
     */
    public LoadInfo getDownloaderInfors(String downPath)
    {
    	if(dao.isHasInfors(downPath))
    	{
    		init(downPath);//第1次下载要进行初始化
    		int range = fileSize/threadCount;//设置每个线程应该下载的长度
    		//range = fileSize%threadCount == 0? fileSize/threadCount : fileSize/threadCount+1;   //计算线程下载量
    		System.out.println("range is:"+range);
    		infos=new ArrayList<DownloadInfo>();//List<DownloadInfo>infos 里装的是每条线程的下载信息
    		//初始化线程信息集合器,初始化每条线程的信息,为每条线程分配开始下载位置，结束位置
    		for(int i=0 ;i<threadCount-1;i++)
    		{
    			//startPos是每条线程数乘以每条线程应该下载的长度,第0条,从0开始
            	//endPos要减去1Byte是因为,不减1byte的地方是下一个线程开始的位置
    			DownloadInfo info = new DownloadInfo(i,i*range,(i+1)*range-1,0,downPath);
    			System.out.println("set threaid："+info.getThreadId()+"startPos:"+info.getStartPos()+"endPos:"+info.getEndPos()+"CompeleteSize:"+info.getCompeleteSize()+"url:"+info.getUrl());
    			infos.add(info);//把每条线程的信息加入到infos这个线程信息集合器里
    		}
    		//这里加入最后1个线程的信息,只所以单独拿出来是因为最后一条线程下载的结束位置应该为fileSize
    		DownloadInfo info = new DownloadInfo(threadCount-1,(threadCount - 1)*range,this.fileSize,0,downPath);
    		System.out.println("set threaid："+info.getThreadId()+"startPos:"+info.getStartPos()+"endPos:"+info.getEndPos()+"CompeleteSize:"+info.getCompeleteSize()+"url:"+info.getUrl());
    		infos.add(info);   	   
    		//创建一个LoadInfo对象记载下载器的具体信息
    		LoadInfo loadInfo = new LoadInfo(this.fileSize,0,downPath);
    		return loadInfo;
    	}
    	else
    	{
    		//如果不是第1次下载,得到数据库中已有的urlstr的下载器的具体信息
    		infos=dao.getInfos(downPath);
    		int size=0;
    		int completeSize=0;
    		for (DownloadInfo info : infos)
    		{
    			completeSize+=info.getCompeleteSize();//把每条线程下载的长度累加起来,得到整个文件的下载长度
    			size+=info.getEndPos()-info.getStartPos()+1;//计算出文件的大小,用每条线程的结束位置减去开始下载的位置,等于每条线程要下载的长度，然后累加
			}
    		LoadInfo loadInfo = new LoadInfo(size,completeSize,downPath);
    		return loadInfo;
    	}
    }
    
    
    /**
     * 初始化,要干的事:1.得到下载文件的长度
     * 2.在给定的保存路径创建文件,设置文件的大小
     */
    private void init(String downPath)
    {
    	try 
    	{
    		String savePath=AppConstant.NetworkConstant.savePath;//保存地址
    		String fileName  = downPath.substring(downPath.lastIndexOf('/')+1); 
			URL url =new URL(downPath);//通过给定的下载地址得到一个url
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();//得到一个http连接
			conn.setConnectTimeout(5*1000);//设置连接超时为5秒钟
			conn.setRequestMethod("GET");//设置连接方式为GET
			//如果http返回的代码是200或者206则为连接成功
			if(conn.getResponseCode()==200||conn.getResponseCode()==206)
			{
				fileSize=conn.getContentLength();//得到文件的大小
				if(fileSize<=0){
					Toast.makeText(getApplicationContext(), "网络故障,无法获取文件大小.", Toast.LENGTH_SHORT).show();
					return;
				}
				File dir = new File(savePath);
				//判斷文件夾是否存在，不存在則創建
				if(!dir.exists())
				{
					if(dir.mkdirs())
					{
						System.out.println("mkdirs success.");
					}
				}
				
				File file=new File(savePath,fileName);
				RandomAccessFile randomFile=new RandomAccessFile(file,"rwd");
				randomFile.setLength(fileSize);//设置保存文件的大小
				randomFile.close();
				conn.disconnect();
			}
		} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
		}
    	
    }
		
	
	/**
	 * 重新下载方法，如果下载暂停了，调用此方法重新下载
	 * **/
	public void reStartDownload(String fileName)
	{	
		String downPath=AppConstant.NetworkConstant.downPath+fileName;//下载地址
		String savePath=AppConstant.NetworkConstant.savePath;//保存地址
		downloader = downloaders.get(downPath);
		if(downloader==null)
		{
			downloader = new Downloader(downPath,savePath,fileName,threadCount,this,mHandler);
			downloaders.put(downPath, downloader);//创建完一个新的下载器,必须把它加入到下载器集合里去
		}
		//LoadInfo是一个实体类,里面封装了一些下载所需要的信息,每个loadinfo对应1个下载器
        LoadInfo loadInfo =getDownloaderInfors(downPath);
        ListState.completeSizes.put(downPath, loadInfo.getComplete());
        ListState.fileSizes.put(downPath, loadInfo.getFileSize());
		 // 调用方法开始下载
        downloader.download(infos);
	}
	
	/**
	 * 改变文件状态的方法，如果文件正在下载，就会暂停，如果暂停则开始下载
	 * **/
	public void setState(String fileName)
	{
		String url=AppConstant.NetworkConstant.downPath+fileName;
		Downloader downloader=downloaders.get(url);
		if(downloader!=null)
		{
			
			int state=	ListState.state.get(fileName);
			if(state == DOWNLOADING)
			{
				//正在下载则暂停
				state =PAUSE;
				ListState.state.put(fileName, state);
				System.out.println(fileName+"暫停");			
			}
			else if(state ==PAUSE)
				{
					//已经停止就开始下载
					reStartDownload(fileName);
					print(fileName+"繼續下載");
				}
			}
		
		else//如果downloaders中没有url的数据,肯定是处于暂停状态，直接开始下载
		{
			ListState.state.put(fileName, INIT);
			reStartDownload(fileName);
		}
	}
	
	private void deleteData(String url)
	{
		print("刪除"+url+"--------------");
		dao.delete(url);//刪除數據庫裏面所有關於這個url的下載信息
		if(downloaders.get(url)!=null)
		{
			
			print("刪除"+url+"數據庫信息");
			downloaders.remove(url);
		}

		if(ListState.completeSizes.get(url)!=null)
		{
			//ListState.completeSizes.remove(url);
			ListState.completeSizes.put(url, 0);
			//這裡之所以不用completeSizes.remove(url)，是因為這裡執行之後，上面的Handler還在執行，
			//那樣的話上面的Handler 就會找不到completeSizes.get(url)，會報空指針異常
			print("清空"+url+"已下載長度");
		}
		if(ListState.fileSizes.get(url)!=null)
		{
			//ListState.fileSizes.remove(url);
			ListState.fileSizes.put(url, 0);
			print("清空"+url+"總長度");
		}
	}
	

	
	@Override
	public void onDestroy() {
		print("Service----------------退出");
		super.onDestroy();
		
	}
	
	private void print(String msg) {
		Log.d("DownloadService", msg);	
	}
}
