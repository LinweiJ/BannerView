package com.lwj.widget.bannerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

/**
 * Created by lwj on 2017/11/8.
 */

public class BannerView extends FrameLayout {

    /**
     * viewpager
     */
    private ViewPager mViewPager;
    /**
     * ForeLayout
     */
    private LinearLayout mForeLayout;
    private ArrayList<String> mPictureUrl;
    private FragmentManager mFragmentManager;
    private OnBannerPageChangeListener mOnBannerPageChangeListener;
    private OnBannerPageSelectedListener mOnBannerPageSelectedListener;
    private boolean mIsCircle;
    /**
     * 动画间隔
     */
    private int mInterval = 4000;
    /**
     * 滑动动画时长
     */
    private float mDurationFavor = 1.0F;
    /**
     * 滑动动画变化速度类型
     */
    private int mInterpolatorType;
    private int mInitItemIndex;
    private BannerScroller mBannerScroller;

    public BannerView(@NonNull Context context) {
        super(context);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 图片列表
     *
     * @param pictureUrl
     */
    public void setPictureUrl(ArrayList<String> pictureUrl) {
        this.mPictureUrl = pictureUrl;
    }

    /**
     * FragmentManager
     *
     * @param fm
     */
    public void setFragmentManager(FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    /**
     * 是否轮播
     *
     * @param isCircle
     */
    public void setCircle(boolean isCircle) {
        this.mIsCircle = isCircle;
    }

    /**
     * 动画间隔
     *
     * @param interval
     */
    public void setInterval(int interval) {
        mInterval = interval;
    }


    /**
     * 滑动动画时长因子
     *
     * @param durationFavor
     */
    public void setDurationFavor(float durationFavor) {

        mDurationFavor = durationFavor;
    }

    /**
     * 滑动动画变化速度类型
     *
     * @param type
     */
    public void setInterpolatorType(int type) {
        mInterpolatorType = type;
    }

    /**
     * 初始页
     *
     * @param position
     */
    public void setInitItem(int position ) {
        if(position>=0&&position<mPictureUrl.size()){
            mInitItemIndex = position;
        }
    }

    /**
     * 初始化图片加载器(只初始化一次)
     *
     * @return
     */
    public void initPictureLoader(BannerPictureLoader loader) {
        if (BannerViewUtil.getInstance().getPictureClickListener() == null) {
            BannerViewUtil.getInstance().setPictureLoader(loader);
        }
    }

    /**
     * 设置图片加载器
     *
     * @return
     */
    public void setPictureLoader(BannerPictureLoader loader) {
        BannerViewUtil.getInstance().setPictureLoader(loader);
    }

    /**
     * 设置图片点击事件
     *
     * @return
     */
    public void setPictureClickListener(OnBannerPictureClickListener listener) {
        BannerViewUtil.getInstance().setPictureClickListener(listener);
    }

    /**
     * ViewPager 滑动监听事件
     */
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener){
        if(mViewPager!=null){
            mViewPager.addOnPageChangeListener(listener);
        }
    }

    /**
     * ViewPager 页面选中事件
     */
    public void setOnBannerPageSelectedListener(OnBannerPageSelectedListener listener){
        mOnBannerPageSelectedListener=listener;
    }

    /**
     * 配置完成后调用
     */
    public void start() {
        initViewPager();
    }

    /**
     * 开始轮播
     */
    public void startAutoPlay() {
        if (mIsCircle) {
            this.postDelayed(this.mAutoRunnable, this.mInterval);
        }
    }

    /**
     * 停止轮播
     */
    public void stopAutoPlay() {
        if (mIsCircle) {
            this.removeCallbacks(this.mAutoRunnable);
        }
    }

    /**
     *  ViewPager
     */
    public ViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * 初始化ViewPager
     */
    protected void initViewPager() {
        mViewPager = new ViewPager(getContext());
        mViewPager.setId(IdUtils.generateViewId());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(params);
        this.addView(mViewPager, 0);
        //设置适配器
        setupBannerPagerAdapter();
        //设置滑动监听事件
        setupPageChangeListener();
        //滑动效果
        setupScroller();
        //初始化
        if(mIsCircle){
            if(mInitItemIndex==0){
                mViewPager.setCurrentItem(1);
            }else if(mInitItemIndex==mPictureUrl.size()-1){
                mViewPager.setCurrentItem(mPictureUrl.size());
            }
        }else {
            if(mInitItemIndex==0&&mOnBannerPageSelectedListener!=null){
                mOnBannerPageSelectedListener.onPageSelected(0,mPictureUrl.get(0));
            }
            mViewPager.setCurrentItem(mInitItemIndex);
        }

        //ForeLayout
        //mForeLayout = new LinearLayout(getContext());
        //mForeLayout.setId(IdUtils.generateViewId());
        //LayoutParams foreLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        //mForeLayout.setLayoutParams(foreLayoutParams);
        //this.addView(mForeLayout);
    }

    /**
     * 间隔mInterval 切换下一张
     */
    protected Runnable mAutoRunnable = new Runnable() {
        @Override
        public void run() {
            boolean canRun = System.currentTimeMillis() - mOnBannerPageChangeListener.getTouchOutTime() > 2000;
            if (mIsCircle && !mOnBannerPageChangeListener.isTouched()&&canRun) {
                int currentItem = mViewPager.getCurrentItem();
                Log.e("Runnable", "" + currentItem);
                mViewPager.setCurrentItem(++currentItem);
            }
            mViewPager.postDelayed(this, mInterval);
        }
    };


    /**
     * 设置滑动器
     *
     * @return
     */
    protected void setupScroller() {
        //插值器
        Interpolator interpolator = BannerInterpolator.getInstance().getInterpolator(mInterpolatorType);
        try {
            Field interpolatorField =ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);
            mBannerScroller = new BannerScroller(getContext(), interpolator,mDurationFavor);
            mBannerScroller.initViewPagerScroll(mViewPager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置适配器
     *
     * @return
     */
    protected void setupBannerPagerAdapter() {
        if (this.mFragmentManager != null) {
            BannerPagerAdapter adapter = new BannerPagerAdapter(this.mFragmentManager, this.mPictureUrl, this.mIsCircle);
            mViewPager.setAdapter(adapter);
        }
    }

    /**
     * 设置滑动监听事件
     *
     * @return
     */
    protected void setupPageChangeListener() {
        mOnBannerPageChangeListener = new OnBannerPageChangeListener(this.mViewPager, this.mPictureUrl.size(), this.mIsCircle){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(mIsCircle&&mPosition == 0){//循环 //首页
                    position=this.mRealNum-1;
                } else if (mIsCircle&&mPosition == this.mRealNum+1) {//循环  //末页
                    position=0;
                }else if(mIsCircle){
                    position--;
                }
                if(mOnBannerPageSelectedListener!=null){
                    mOnBannerPageSelectedListener.onPageSelected(position,mPictureUrl.get(position));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == SCROLL_STATE_IDLE) {//滑动动画做完的状态
                    mBannerScroller.setScrollFactor(mDurationFavor);
                }
                if(state == SCROLL_STATE_DRAGGING){//手动拖动
                    mBannerScroller.setScrollFactor(1.0F);
                }
                if(state == SCROLL_STATE_SETTLING){//手放开
                }
            }
        };
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(mOnBannerPageChangeListener);
        }
    }

}
