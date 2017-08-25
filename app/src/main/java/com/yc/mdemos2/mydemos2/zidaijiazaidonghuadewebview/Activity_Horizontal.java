package com.yc.mdemos2.mydemos2.zidaijiazaidonghuadewebview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yc.mdemos2.mydemos2.R;


public class Activity_Horizontal extends Activity{

	private LJWebView mLJWebView = null;
	private String url = "http://www.baidu.com";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

		mLJWebView = (LJWebView) findViewById(R.id.web);
		mLJWebView.setBarHeight(8);
		mLJWebView.setClickable(true);
		mLJWebView.setUseWideViewPort(true);
		mLJWebView.setSupportZoom(true);
		mLJWebView.setBuiltInZoomControls(true);
		mLJWebView.setJavaScriptEnabled(true);
		mLJWebView.setCacheMode(WebSettings.LOAD_NO_CACHE);
		mLJWebView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				System.out.println("跳的URL =" + url);

				view.loadUrl(url);
				return true;
			}
		});

		mLJWebView.loadUrl(url);


	}

}
