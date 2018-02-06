package com.lwj.widget.bannerview;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by lwj on 2018/2/3.
 */

public class BannerInterpolator  {

    public final static int LINEAR = 0;//直线
    public final static int ACCELERATE = 1;//在动画开始的地方速率改变比较慢，然后开始加速
    public final static int DECELERATE = 2;//在动画开始的地方快然后慢
    public final static int ACCELERATE_DECELERATE = 3;//在动画开始与介绍的地方速率改变比较慢，在中间的时候加速

    public static BannerInterpolator INSTANCE;

    private BannerInterpolator() {
    }

    public static BannerInterpolator getInstance() {

        synchronized (BannerInterpolator.class) {
            if (INSTANCE == null) {
                INSTANCE = new BannerInterpolator();
            }
            return INSTANCE;
        }

    }

    /**
     * 0~1
     *
     * @param type
     * @return
     */
    public Interpolator getInterpolator(int type) {
        Interpolator interpolator;
        switch (type) {
            case LINEAR://直线
                interpolator = new LinearInterpolator();
                break;
            case ACCELERATE://在动画开始的地方速率改变比较慢，然后开始加速
                interpolator = new AccelerateInterpolator();
                break;
            case  DECELERATE://在动画开始的地方快然后慢
                interpolator= new DecelerateInterpolator();
                break;
            case   ACCELERATE_DECELERATE://在动画开始与介绍的地方速率改变比较慢，在中间的时候加速
                interpolator= new AccelerateDecelerateInterpolator();
                break;
            default://直线
                interpolator = new LinearInterpolator();
                break;
        }
        return interpolator;
    }

}
