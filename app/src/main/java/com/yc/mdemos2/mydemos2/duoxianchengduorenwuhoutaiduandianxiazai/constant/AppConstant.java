package com.yc.mdemos2.mydemos2.duoxianchengduorenwuhoutaiduandianxiazai.constant;

public class AppConstant 
{
	public class MainConstant
	{
		//标签的名字
		public static final String netTabName="下載完成";
		public static final String localTabName="正在下载";
	}
	
	public class NetworkConstant
	{
		//downPath为下载地址
		public static final String downPath="http://10.147.140.245:8080/mp3/";
		//savePath为保存音频的路径
		public static final String savePath="/mnt/sdcard/localDown/";

	}
	
	public class AdapterConstant
	{
		public static final String down_over="下载完成";
		public static final String delete="文件已经删除！";
	}
	
	public class DownloadServiceConstant
	{
		public static final String downOverAction="down_over_action";
	}
	
	public class LocalActivityConstant
	{
		public static final String update_action="updateUI";
	}
}
