package com.lwj.widget.bannerview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by lwj on 2017/11/6.
 */

public class BannerScroller extends Scroller {


    private float mScrollFactor=1.0F;
    private int mDuration;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, int duration) {
        super(context, null);
        mDuration = duration;
    }
    public BannerScroller(Context context, Interpolator interpolator, float scrollFactor) {
        super(context, interpolator);
        mScrollFactor = scrollFactor;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public void setScrollFactor(float scrollFactor) {
        this.mScrollFactor = scrollFactor;
    }

    public float getScrollFactor() {
        return mScrollFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, this.mDuration);
    }


    /**
     * 设置滚动切换的时间
     */
    public void setScrollDurationFactor(float scrollFactor) {
         mScrollFactor = scrollFactor;
    }

    /**
     * A simple {@link ViewPager#smoothScrollTo} subclass.
     *
     * @param startX
     * @param startY
     * @param dx
     * @param dy
     * @param duration
     */
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int) (duration*mScrollFactor));
    }


    public void initViewPagerScroll(ViewPager viewPager) {
        try {


            Field mScroller = viewPager.getClass().getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
