package com.lwj.widget.bannerview;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

/**
 * Created by lwj on 2018/2/6.
 * 作用于{@link BannerFragment#showPicture(Fragment, ImageView, String)}的图片加载器
 *
 */

public  interface BannerPictureLoader {

         void showPicture(Fragment fragment, ImageView pictureView, String pictureUrl);

}
