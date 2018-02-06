package com.lwj.widget.bannerview;

/**
 * Created by Lwj on 2018/2/1.
 */

public class BannerViewUtil {

    public static BannerViewUtil INSTANCE;
    public BannerPictureLoader mPictureLoader;
    public OnBannerPictureClickListener mPictureClickListener;

    public static BannerViewUtil getInstance() {

        synchronized (BannerViewUtil.class){
            if(INSTANCE==null){
                INSTANCE=new BannerViewUtil();
            }
            return INSTANCE;
        }

    }

    public BannerPictureLoader getPictureLoader(){

        return mPictureLoader;
    }

    public void setPictureLoader(BannerPictureLoader pictureLoader){

        mPictureLoader=pictureLoader;

    }

    public OnBannerPictureClickListener getPictureClickListener(){

        return mPictureClickListener;
    }

    public void setPictureClickListener(OnBannerPictureClickListener pictureClickListener){

        mPictureClickListener=pictureClickListener;

    }

}
