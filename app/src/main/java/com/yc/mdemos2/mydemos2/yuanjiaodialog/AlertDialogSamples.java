/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yc.mdemos2.mydemos2.yuanjiaodialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.yc.mdemos2.mydemos2.R;

public class AlertDialogSamples extends Activity {
	private static final int FIRST_CUSTOMDIALOG = 1;

	@Override
	protected Dialog onCreateDialog(int id) {
		/***
		 * 用switch语句的好处：
		 * 1、逻辑清晰，方便阅读代码
		 * 2、一个Activity里或许要用到很多dialog，都放在这里方便管理
		 */
		switch (id) {
			case FIRST_CUSTOMDIALOG:
				/**
				 * 以下两种情况声明LayoutInflater对象都可以，用的时候看情况而定
				 */
				LayoutInflater factory = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				// LayoutInflater factory = LayoutInflater.from(this);
				final View customView = factory.inflate(
						R.layout.alert_dialog_text_entry, null);

				Dialog ad = new Dialog(this, R.style.Theme_CustomDialog2);
				ad.setContentView(customView);
				ad.setCancelable(false);//有可能你调不出这个方法，可以通过ad.setOnKeyListener()方法来实现屏蔽back键
				ad.setCanceledOnTouchOutside(false);//当点击除对话框外屏幕其他区域时对话框不消失，默认是消失的
				/**
				 * 还可以继续在下面添加控件和控件监听事件
				 */
				Button button = (Button)customView.findViewById(R.id.yes);
				button.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Toast.makeText(AlertDialogSamples.this, "你点击了确定按钮", 1).show();
					}
				});
				return ad;
		}
		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.alert_dialog);
		Button first_customdialog = (Button) findViewById(R.id.first_customdialog);
		first_customdialog.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(FIRST_CUSTOMDIALOG);
			}
		});
	}
}
