package com.yc.mdemos2.mydemos2.texiaosousuokuang.ui.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.mdemos2.mydemos2.R;
import com.yc.mdemos2.mydemos2.texiaosousuokuang.base.util.UI;

/**
 * ActionSearchBar
 * @author 515536
 * @date 2014-6-6
 *
 */
public class ActionSearchBar extends Fragment implements OnClickListener {

    private static final String ARG_CANCELABLE_ONTOUCHOUTSIDE = "cancelable_ontouchoutside";
    private static final int CANCEL_BUTTON_ID = 100;
    private static final int BG_VIEW_ID = 10;

    private boolean mDismissed = true;
    private ActionSearchBarListener mListener;
    private View mView;
    private Attributes mAttrs;
    private boolean isCancel = true;

    private Animation mSearchAnim;
    private ImageView ivBtnAroundImage;
    private TextView tvType;
    private ImageView ivSearchBtn;
    private boolean mAnimState = false;
    private int intWidth;
    private int nowWidth;
    private int maxLength = 600;
    private EditText etText;

    public void show(int id,FragmentManager manager, String tag) {
        if (!mDismissed) {
            return;
        }
        mDismissed = false;
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(id,this, tag);
//		ft.addToBackStack(null);
        ft.commit();
    }

    public void dismiss() {
        if (mDismissed) {
            return;
        }
        mDismissed = true;
        getFragmentManager().popBackStack();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(this);
        ft.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        InputMethodManager imm = (InputMethodManager) getActivity()
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (!imm.isActive()) {
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        }

        mView = inflater.inflate(R.layout.searchbar_layout, null);
        mAttrs = readAttribute();

        findView();
        setView();
        return mView;
    }

    private void findView() {
        ivBtnAroundImage = UI.findViewById(mView, R.id.iv_search_background);
        ivSearchBtn = UI.findViewById(mView, R.id.iv_search_btn);
        tvType = UI.findViewById(mView, R.id.tv_type);
        etText = UI.findViewById(mView, R.id.et_text);

        ivBtnAroundImage.setBackgroundDrawable(mAttrs.btnAroundImageOffExpend);
        ivSearchBtn.setBackgroundDrawable(newSelector(getActivity(), mAttrs.btnImageCheckOff, mAttrs.btnImageCheckOn, null, null));
        tvType.setBackgroundDrawable(mAttrs.tvBackground);
        tvType.setCompoundDrawablesWithIntrinsicBounds(null,null,newSelector(getActivity(),mAttrs.tvImageCheckOff,mAttrs.tvImageCheckOn,null,null),null);
        tvType.setCompoundDrawablePadding(mAttrs.padding);
        tvType.setTextSize(mAttrs.actionSheetTextSize);
        tvType.setPadding(0,0, mAttrs.dp2px(10),0);
        maxLength = mAttrs.maxLength > 300 ? mAttrs.maxLength : 300;

        ivBtnAroundImage.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tvType.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        intWidth = ivBtnAroundImage.getMeasuredWidth();

        etText.getLayoutParams().width = maxLength - intWidth - tvType.getMeasuredWidth()-mAttrs.dp2px(30);
        etText.requestLayout();
    }

    private void setView() {
        mSearchAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (mAnimState){
                    ivBtnAroundImage.getLayoutParams().width = nowWidth - (int) (interpolatedTime * ((maxLength - 300) - (maxLength - nowWidth)));
                    if (interpolatedTime == 1) {
                        ivBtnAroundImage.getLayoutParams().width = intWidth;
                    }
                }else {
                    ivBtnAroundImage.getLayoutParams().width = (int) (interpolatedTime * (maxLength - nowWidth)) + nowWidth;
                }
                ivBtnAroundImage.requestLayout();
            }
        };
        mSearchAnim.setInterpolator(new DecelerateInterpolator());
        mSearchAnim.setDuration(200);

        mSearchAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (!mAnimState) {
                    ivBtnAroundImage.setBackgroundDrawable(mAttrs.btnAroundImageOnExpend);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mAnimState) {
                    ivBtnAroundImage.setBackgroundDrawable(mAttrs.btnAroundImageOffExpend);
                }
            }
        });

        ivSearchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnimState = (tvType.getVisibility() == View.VISIBLE);
                mSearchAnim.setInterpolator(mAnimState ? new AccelerateInterpolator() : new DecelerateInterpolator());
                Animation animation;
                nowWidth = ivBtnAroundImage.getLayoutParams().width > 0 ? ivBtnAroundImage.getLayoutParams().width : intWidth;
                if (mAnimState) {
                    tvType.setVisibility(View.INVISIBLE);
                    animation = new AlphaAnimation(1.0f, 0.1f);
                } else {
                    tvType.setVisibility(View.VISIBLE);
                    animation = new AlphaAnimation(0.1f, 1.0f);
                }
                animation.setDuration(500);
                tvType.setVisibility(mAnimState ? View.INVISIBLE : View.VISIBLE);
                etText.setVisibility(mAnimState ? View.INVISIBLE : View.VISIBLE);
                if (!mAnimState) etText.requestFocus();
                ivSearchBtn.startAnimation(mSearchAnim);
                tvType.startAnimation(animation);
                etText.startAnimation(animation);
            }
        });

        etText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {
                    mListener.onEnter(tvType.getText().toString(), etText.getText().toString());
                    return true;
                }
                return false;

            }
        });
    }

    @Override
    public void onDestroyView() {
//		if (mListener != null) {
//			mListener.onDismiss(this, isCancel);
//		}
        super.onDestroyView();
    }

    private Attributes readAttribute() {
        Attributes attrs = new Attributes(getActivity());
        TypedArray a = getActivity().getTheme().obtainStyledAttributes(null,
                R.styleable.ActionSearchBar, R.attr.actionSearchBarStyle, 0);
        Drawable btnImageCheckOn = a
                .getDrawable(R.styleable.ActionSearchBar_btnImageCheckOn);
        if (btnImageCheckOn != null) {
            attrs.btnImageCheckOn = btnImageCheckOn;
        }
        Drawable btnImageCheckOff = a
                .getDrawable(R.styleable.ActionSearchBar_btnImageCheckOff);
        if (btnImageCheckOff != null) {
            attrs.btnImageCheckOff = btnImageCheckOff;
        }
        Drawable tvBackground = a
                .getDrawable(R.styleable.ActionSearchBar_tvBackground);
        if (tvBackground != null) {
            attrs.tvBackground = tvBackground;
        }
        Drawable tvImageCheckOn = a
                .getDrawable(R.styleable.ActionSearchBar_tvImageCheckOn);
        if (tvImageCheckOn != null) {
            attrs.tvImageCheckOn = tvImageCheckOn;
        }
        Drawable tvImageCheckOff = a
                .getDrawable(R.styleable.ActionSearchBar_tvImageCheckOff);
        if (tvImageCheckOff != null) {
            attrs.tvImageCheckOff = tvImageCheckOff;
        }
        Drawable btnAroundImageOnExpend = a
                .getDrawable(R.styleable.ActionSearchBar_btnAroundImageOnExpend);
        if (btnAroundImageOnExpend != null) {
            attrs.btnAroundImageOnExpend = btnAroundImageOnExpend;
        }
        Drawable btnAroundImageOffExpend = a
                .getDrawable(R.styleable.ActionSearchBar_btnAroundImageOffExpend);
        if (btnAroundImageOffExpend != null) {
            attrs.btnAroundImageOffExpend = btnAroundImageOffExpend;
        }
        attrs.maxLength = (int) a.getDimension(
                R.styleable.ActionSearchBar_maxLength,attrs.maxLength);
        attrs.padding = (int) a.getDimension(
                R.styleable.ActionSearchBar_tvImagePadding, attrs.padding);
        attrs.actionSheetTextSize = a.getDimensionPixelSize(R.styleable.ActionSearchBar_actionSearchBarTextSize, (int) attrs.actionSheetTextSize);

        a.recycle();
        return attrs;
    }

    private boolean getCancelableOnTouchOutside() {
        return getArguments().getBoolean(ARG_CANCELABLE_ONTOUCHOUTSIDE);
    }

    public void setActionSearchBarListener(ActionSearchBarListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
//		if (v.getId() == BG_VIEW_ID && !getCancelableOnTouchOutside()) {
//			return;
//		}
//		dismiss();
//		if (v.getId() != CANCEL_BUTTON_ID && v.getId() != BG_VIEW_ID) {
//			if (mListener != null) {
//				mListener.onOtherButtonClick(this, v.getId() - CANCEL_BUTTON_ID
//						- 1);
//			}
//			isCancel = false;
//		}
    }

    public static Builder createBuilder(int id,Context context,
                                        FragmentManager fragmentManager) {
        return new Builder(id,context, fragmentManager);
    }

    private static class Attributes {
        private Context mContext;

        public Attributes(Context context) {
            mContext = context;
            this.btnImageCheckOn = new ColorDrawable(Color.TRANSPARENT);
            this.btnImageCheckOff = new ColorDrawable(Color.BLACK);
            ColorDrawable gray = new ColorDrawable(Color.GRAY);
            this.tvBackground = gray;
            this.tvImageCheckOn = gray;
            this.tvImageCheckOff = gray;
            this.btnAroundImageOnExpend = gray;
            this.btnAroundImageOffExpend = gray;
            this.padding = dp2px(20);
            this.actionSheetTextSize = dp2px(16);
            this.maxLength = dp2px(600);
        }

        private int dp2px(int dp){
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dp, mContext.getResources().getDisplayMetrics());
        }

        Drawable btnImageCheckOn;
        Drawable btnImageCheckOff;
        Drawable tvBackground;
        Drawable tvImageCheckOn;
        Drawable tvImageCheckOff;
        Drawable btnAroundImageOnExpend;
        Drawable btnAroundImageOffExpend;
        int padding;
        float actionSheetTextSize;
        int maxLength;
    }

    public static class Builder {

        private int id;
        private Context mContext;
        private FragmentManager mFragmentManager;
        private String mTag = "actionSearchBar";
        private boolean mCancelableOnTouchOutside;
        private ActionSearchBarListener mListener;

        public Builder(int id, Context context, FragmentManager fragmentManager) {
            this.id = id;
            mContext = context;
            mFragmentManager = fragmentManager;
        }

        public Builder setTag(String tag) {
            mTag = tag;
            return this;
        }

        public Builder setListener(ActionSearchBarListener listener) {
            this.mListener = listener;
            return this;
        }

        public Builder setCancelableOnTouchOutside(boolean cancelable) {
            mCancelableOnTouchOutside = cancelable;
            return this;
        }

        public Bundle prepareArguments() {
            Bundle bundle = new Bundle();
            bundle.putBoolean(ARG_CANCELABLE_ONTOUCHOUTSIDE,
                    mCancelableOnTouchOutside);
            return bundle;
        }

        public ActionSearchBar show() {
            ActionSearchBar actionSearchBar = (ActionSearchBar) Fragment.instantiate(
                    mContext, ActionSearchBar.class.getName(), prepareArguments());
            actionSearchBar.setActionSearchBarListener(mListener);
            actionSearchBar.show(id,mFragmentManager, mTag);
            return actionSearchBar;
        }

    }

    public static interface ActionSearchBarListener {

//		void onDismiss(ActionSearchBar actionSearchBar, boolean isCancel);
//
//		void onOtherButtonClick(ActionSearchBar actionSearchBar, int index);

        void onEnter(String type, String text);
    }

    /** 设置Selector。 */
    public static StateListDrawable newSelector(Context context, Drawable normal, Drawable pressed, Drawable focused,
                                                Drawable unable) {
        StateListDrawable bg = new StateListDrawable();
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[] {}, normal);
        return bg;
    }

}