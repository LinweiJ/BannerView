package com.lwj.widget.bannerview;

import android.support.v4.view.ViewPager;
import android.util.Log;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

/**
 * Created by lwj on 2017/11/6.
 * banner处理首末页切换
 */

public class OnBannerPageChangeListener implements ViewPager.OnPageChangeListener {

    protected int mPosition;
    protected ViewPager mViewPager;
    protected int mRealNum;
    protected boolean mIsCircle;
    protected boolean mTouched;

    protected long mTouchOutTime;

    public OnBannerPageChangeListener(ViewPager viewPager, int realNum) {

        this(viewPager,realNum, false);
    }

    public OnBannerPageChangeListener(ViewPager viewPager, int realNum, boolean isCircle) {
        this.mViewPager = viewPager;
        this.mRealNum = realNum;
        this.mIsCircle = isCircle;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
    }
    /**
     * SCROLL_STATE_DRAGGING（1）表示用户手指“按在屏幕上并且开始拖动”的状态（手指按下但是还没有拖动的时候还不是这个状态，只有按下并且手指开始拖动后。）
     * SCROLL_STATE_IDLE（0）滑动动画做完的状态。
     * SCROLL_STATE_SETTLING（2）在“手指离开屏幕”的状态。
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == SCROLL_STATE_IDLE&& this.mIsCircle ) {//滑动动画做完的状态
            if (mPosition == 0) {
                mViewPager.setCurrentItem(this.mRealNum, false);
            } else if (mPosition == this.mRealNum+1) {
                mViewPager.setCurrentItem(1, false);
            }
        }
        if(state == SCROLL_STATE_DRAGGING){
            mTouched =true;
        }
        if(state == SCROLL_STATE_SETTLING){
            mTouched =false;
            mTouchOutTime = System.currentTimeMillis();
        }
    }

    public boolean isTouched(){

        return mTouched;
    }

    public long getTouchOutTime() {
        return mTouchOutTime;
    }
}
