package com.yc.mdemos2.mydemos2.fangwangyelanmuguanli.common.util;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;


public class FileTools {

	public static BufferedReader bufread;
	// 指定文件路径和名称
	private static String readStr = "";
	private static String SDPATH = "";

	/**
	 * 创建文本文件.
	 * 
	 * @throws IOException
	 * 
	 */
	public static void creatTxtFile(File filename) throws IOException {
		if (!filename.exists()) {
			filename.createNewFile();
			System.err.println(filename + "已创建！");
		}
	}

	public static boolean isExists(File file) {
		boolean b = false;
		if (file.exists())
			b = true;
		else
			b = false;
		return b;
	}

	public static void updateFile(String url, String filename, String savepath) {
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		HttpClient client = new DefaultHttpClient();
		try {
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			long length = entity.getContentLength();
			InputStream is = entity.getContent();
			FileOutputStream fileOutputStream = null;
			if (is != null) {
				File path = new File(savepath);
				if (!path.exists()) {
					path.mkdirs();
				}
				File file = new File(savepath, filename);
				if (!file.exists()) {
					fileOutputStream = new FileOutputStream(file);
					byte[] buf = new byte[1024];
					int ch = -1;
					int count = 0;
					while ((ch = is.read(buf)) != -1) {
						fileOutputStream.write(buf, 0, ch);
						count += ch;
						if (length > 0) {
						}
					}

				}
				fileOutputStream.flush();
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("FileError", e.toString());
		}

	}

	/**
	 * 读取文本文件.
	 * 
	 */
	public static String readTxtFile(File filename) {
		String read;
		FileReader fileread;
		try {
			fileread = new FileReader(filename);
			bufread = new BufferedReader(fileread);
			try {
				while ((read = bufread.readLine()) != null) {
					readStr = readStr + read;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("文件内容是:" + "\r\n" + readStr);
		return readStr;
	}
	
	/**
	 * 读取文本文件.
	 * @throws Exception 
	 * 
	 */
	public static String readTxtFile(InputStream is) throws Exception {
	     int size = is.available();   
	     // Read the entire asset into a local byte buffer.   
	     byte[] buffer = new byte[size];   
	     is.read(buffer);   
	     is.close();   
	     // Convert the buffer into a string.   
	     String text = new String(buffer, "UTF-8");
	     return text;
	}
	
	 

	/**
	 * 写文件.
	 * 
	 */
	public static void writeTxtFile(File filename, String newStr)
			throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		String filein = newStr + "\r\n" + readStr + "\r\n";
		RandomAccessFile mm = null;
		try {
			mm = new RandomAccessFile(filename, "rw");
			mm.writeBytes(filein);
		} catch (IOException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		} finally {
			if (mm != null) {
				try {
					mm.close();
				} catch (IOException e2) {
					// TODO 自动生成 catch 块
					e2.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将文件中指定内容的第一行替换为其它内容.
	 * 
	 * @param oldStr
	 *            查找内容
	 * @param replaceStr
	 *            替换内容
	 */
	public static void replaceTxtByStr(File filename, String oldStr,
			String replaceStr) {
		String temp = "";
		try {
			// File file = new File(path);
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// 保存该行前面的内容
			for (int j = 1; (temp = br.readLine()) != null
					&& !temp.equals(oldStr); j++) {
				buf = buf.append(temp);
				buf = buf.append(System.getProperty("line.separator"));
			}

			// 将内容插
			buf = buf.append(replaceStr);

			// 保存该行后面的内容
			while ((temp = br.readLine()) != null) {
				buf = buf.append(System.getProperty("line.separator"));
				buf = buf.append(temp);
			}

			br.close();
			FileOutputStream fos = new FileOutputStream(filename);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
