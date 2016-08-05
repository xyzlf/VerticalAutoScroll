package com.xyzlf.vertical.autoscroll;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.xyzlf.vertical.autoscroll.library.R;

/**
 * Created by zhanglifeng on 2016/8/2.
 * Vertical auto scroll view
 */

public class VerticalScrollView extends LinearLayout {

    private static final int DEFAULT_INTERVAL_DURATION = 4000; //间隔时间
    private final int DEFAULT_ANIM_DURATION = 1000; //动画间隔时间
    private static final float DEFAULT_HEIGHT = 50; // 单位dp

    private int mDefaultHeight; //控件默认高度
    private int intervalDuration;
    private int animDuration;

    //显示的view
    private View mFirstView;
    private View mSecondView;
    private int mPosition;
    private boolean isStarted;

    private VerticalScrollAdapter mAdapter;

    public VerticalScrollView(Context context) {
        this(context, null);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VerticalScrollView, defStyleAttr, 0);
        intervalDuration = array.getInteger(R.styleable.VerticalScrollView_intervalDuration, DEFAULT_INTERVAL_DURATION);
        animDuration = array.getInteger(R.styleable.VerticalScrollView_animDuration, DEFAULT_ANIM_DURATION);
        array.recycle();

        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        mDefaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_HEIGHT, getResources().getDisplayMetrics());
    }

    public void setAdapter(VerticalScrollAdapter adapter) {
        this.mAdapter = adapter;
        if (null == adapter) {
            throw new RuntimeException("VerticalScrollAdapter is null. Please check your code.");
        }
        setupAdapter();
    }

    private void setupAdapter() {
        removeAllViews();
        if (mAdapter.getCount() == 1) {
            mFirstView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            addView(mFirstView);
        } else if (mAdapter.getCount() > 1) {
            mFirstView = mAdapter.getView(this);
            mSecondView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            mAdapter.setItem(mSecondView, mAdapter.getItem(1));
            addView(mFirstView);
            addView(mSecondView);
            mPosition = 1;
            isStarted = false;
        }
    }

    public void start() {
        if (!isStarted && null != mAdapter && mAdapter.getCount() > 1) {
            isStarted = true;
            postDelayed(mRunnable, intervalDuration);
        }
    }

    public void stop() {
        removeCallbacks(mRunnable);
        isStarted = false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /**
     * 屏幕 旋转
     *
     * @param newConfig newConfig
     */
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * @param widthMeasureSpec  width
     * @param heightMeasureSpec height
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (LayoutParams.WRAP_CONTENT == getLayoutParams().height) {
            getLayoutParams().height = mDefaultHeight;
        }
        mDefaultHeight = getMeasuredHeight();
        if (mFirstView != null) {
            mFirstView.getLayoutParams().height = mDefaultHeight;
        }
        if (mSecondView != null) {
            mSecondView.getLayoutParams().height = mDefaultHeight;
        }
    }

    private AnimRunnable mRunnable = new AnimRunnable();

    private class AnimRunnable implements Runnable {
        @Override
        public void run() {
            performSwitch();
            postDelayed(this, intervalDuration);
        }
    }

    private void performSwitch() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mFirstView, "translationY", mFirstView.getTranslationY() - mDefaultHeight);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mSecondView, "translationY", mSecondView.getTranslationY() - mDefaultHeight);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1, animator2);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFirstView.setTranslationY(0);
                mSecondView.setTranslationY(0);
                View removedView = getChildAt(0);
                mPosition++;
                mAdapter.setItem(removedView, mAdapter.getItem(mPosition % mAdapter.getCount()));
                removeView(removedView);
                addView(removedView, 1);
            }
        });
        set.setDuration(animDuration);
        set.start();
    }

}
