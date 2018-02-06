package com.lwj.widget.bannerview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lwj on 2018/2/6.
 * A simple {@link Fragment} subclass.
 */
public class BannerFragment extends Fragment {

    private static String INDEX="10200";
    private static String URLS="10201";
    private ArrayList<String> mUrls;
    private View mLayout;
    private int mIndex;


    public BannerFragment() {
        // Required empty public constructor
    }


    public  static BannerFragment newInstance(int index, ArrayList<String> urls ) {

        Bundle args = new Bundle();
        args.putInt(INDEX,index);
        args.putStringArrayList(URLS,urls);
        BannerFragment bannerFragment = new BannerFragment();
        bannerFragment.setArguments(args);
        return bannerFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(mLayout==null)
        {
            if(getArguments()!=null)
            {
                mIndex = getArguments().getInt(INDEX);
                mUrls = getArguments().getStringArrayList(URLS);
            }
            mLayout = inflater.inflate(R.layout.fragment_banner, container, false);
            ImageView ivPicture = (ImageView) mLayout.findViewById(R.id.iv_picture);

            //显示图片
            String url=mUrls.get(mIndex);

            showPicture(this,ivPicture,url);
            //点击事件
            ivPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPictureClick(BannerFragment.this, mIndex, mUrls);
                }
            });

        }

        return mLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    /**
     * 显示图片
     * @param pictureUrl
     */
    public  void showPicture(Fragment fragment, ImageView ivPicture, String pictureUrl){
        BannerPictureLoader pictureLoader = BannerViewUtil.getInstance().getPictureLoader();
        if(pictureLoader!=null)
        {
            pictureLoader.showPicture(fragment,ivPicture,pictureUrl);
        }
    }


    /**
     * 点击图片
     * @param pictureUrls
     */
    public  void  onPictureClick(Fragment fragment, int position, List<String> pictureUrls){
        OnBannerPictureClickListener pictureClickListener = BannerViewUtil.getInstance().getPictureClickListener();
        if(pictureClickListener!=null)
        {
            pictureClickListener.onPictureClick(fragment,position,pictureUrls);
        }

    }

}
