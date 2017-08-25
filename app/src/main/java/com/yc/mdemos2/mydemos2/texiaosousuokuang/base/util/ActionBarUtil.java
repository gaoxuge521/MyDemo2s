package com.yc.mdemos2.mydemos2.texiaosousuokuang.base.util;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.mdemos2.mydemos2.R;

/**
 * 带“返回”按钮的顶部标题的统一初始化，很多页面包含这个界面元素
 */
public class ActionBarUtil {

	public interface OnBackListener {
		void onBackFinish();
	}

	public static void initTitle( Activity activity, String title) {
		TextView mTopBarTitleName = (TextView)activity.findViewById(R.id.title_layout_name_TV);
		mTopBarTitleName.setText(title); 
	}

	public static void setTitleCompoundDrawables( Activity activity,int left,int top,int right,int bottom){
		TextView mTopBarTitleName = (TextView)activity.findViewById(R.id.title_layout_name_TV);
		mTopBarTitleName.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
	}

	/**
	 * 初始化右边按钮
	 * @param activity
	 * @param drawableID 按钮图片
	 * @param isHide 是否隐藏整个右边
	 * @param listener 点击监听
	 */
	public static void initRight(final Activity activity,int drawableID, boolean isHide,final OnBackListener listener) {
		if(drawableID>0){
			ImageView mTopRightBtn = (ImageView)activity.findViewById(R.id.title_layout_right_button);
			mTopRightBtn.setImageResource(drawableID);			
		}
		View mRightRL = activity.findViewById(R.id.title_layout_right_RL);
		if(isHide){
			mRightRL.setVisibility(View.INVISIBLE);
		}
		if(listener!=null){
			mRightRL.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					closeInputMethod(activity);
					if(listener != null)
						listener.onBackFinish();
				}
			});
		}
	}

	/** 初始化右边第二个按钮，默认是隐藏的
	 * @param activity
	 * @param drawableID 按钮图片
	 * @param isHide 是否隐藏整个右边
	 * @param listener 点击监听
	 */
	public static void initRight2(final Activity activity,int drawableID, boolean isHide,final OnBackListener listener){
		ImageView mTopRightBtn = (ImageView)activity.findViewById(R.id.title_layout_right2_button);
		if(drawableID>0){
			mTopRightBtn.setImageResource(drawableID);			
		}
		if(isHide){
			mTopRightBtn.setVisibility(View.GONE);
		}else{
			mTopRightBtn.setVisibility(View.VISIBLE);
		}
		if(listener!=null){
			mTopRightBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					closeInputMethod(activity);
					if(listener != null){
						listener.onBackFinish();
					}
				}
			});
		}
	}


	public static void showRightBtn(Activity activity) {
		View mRightRL = activity.findViewById(R.id.title_layout_right_RL);
		mRightRL.setVisibility(View.VISIBLE);
	}

	//	protected static void setTopRightBtn(Activity activity,int drawableID){
	//		ImageView mTopRightBtn = (ImageView)activity.findViewById(R.id.title_layout_right_button);
	//		mTopRightBtn.setImageResource(drawableID);
	//	}

	public static void initLeft(final Activity activity, final OnBackListener listener) {
		View mLeftRL = activity.findViewById(R.id.title_layout_left_RL);
		mLeftRL.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				closeInputMethod(activity);
				if(listener != null){
					listener.onBackFinish();
				}
				activity.finish();
			}
		});
	}

	public static void initCenter(final Activity activity, final OnBackListener listener) {
		View mCenterRL = activity.findViewById(R.id.title_layout_title_LL);
		if(listener!=null){
			mCenterRL.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					closeInputMethod(activity);
					if(listener != null){
						listener.onBackFinish();
					}
				}
			});
		}
	}


	public static  void	isHideTopTitleLayout(Activity activity){
		View mTopTitleView = activity.findViewById(R.id.titleLayout);
		mTopTitleView.setVisibility(View.GONE);
	}

	/**
	 * 关闭输入法弹出
	 * @param activity
	 */
	public static void closeInputMethod(final Activity activity){
		if(activity != null && activity.getCurrentFocus() != null ) {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
			boolean isOpen = imm.isActive();
			if(isOpen){
				imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
			}
		}
	}
}
