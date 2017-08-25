package com.yc.mdemos2.mydemos2.fangzhenfanye;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class turntest extends Activity {
	/** Called when the activity is first created. */
	private Paint mPaint;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new PageWidget(this));

	}

}