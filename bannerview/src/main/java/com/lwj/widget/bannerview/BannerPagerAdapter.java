package com.lwj.widget.bannerview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by lwj on 2017/11/6.
 */

public class BannerPagerAdapter extends FragmentPagerAdapter {

    private final boolean mIsCircle;
    private ArrayList<String> mUrlList = new ArrayList<>();
    private int mRealNum;
    private int mExtendNum;

    public BannerPagerAdapter(FragmentManager fm, ArrayList<String> urlList, boolean isCircle) {
        super(fm);
        mUrlList.clear();
        mUrlList.addAll(urlList);
        mRealNum = urlList.size();//实际数量 如:7
        mIsCircle = isCircle;
        if (mIsCircle) {
            //扩大 左右各增加一页
            mExtendNum = mUrlList.size() + 2;//扩大数量 如:7+2=9
        } else {
            mExtendNum = mUrlList.size();
        }
    }

    @Override
    public int getCount() {
        return mExtendNum;
    }

    @Override
    public Fragment getItem(int position) {
        int index;
        if (mIsCircle && position == 0) {
            //扩大 左右各增加一页
            index = mUrlList.size() - 1;
        } else if (mIsCircle && position == mUrlList.size() + 1) {
            index = 0;
        } else if (mIsCircle) {
            index = position - 1;
        } else {
            index = position;
        }
        BannerFragment fragment = BannerFragment.newInstance(index, mUrlList);
        return fragment;
    }
}
