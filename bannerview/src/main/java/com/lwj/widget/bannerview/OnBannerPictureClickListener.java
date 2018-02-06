package com.lwj.widget.bannerview;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by lwj on 2017/2/1.
 * 作用于{@link BannerFragment#onPictureClick(Fragment, int, List)}的图片加载器
 *
 */

public  interface OnBannerPictureClickListener {

         void onPictureClick(Fragment fragment, int position, List<String> pictureUrl);

}
