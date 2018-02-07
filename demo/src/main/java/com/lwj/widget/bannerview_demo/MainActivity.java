package com.lwj.widget.bannerview_demo;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lwj.widget.bannerview.BannerInterpolator;
import com.lwj.widget.bannerview.BannerPictureLoader;
import com.lwj.widget.bannerview.BannerView;
import com.lwj.widget.bannerview.OnBannerPageSelectedListener;
import com.lwj.widget.bannerview.OnBannerPictureClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTv_hint;
    private BannerView mBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<String> mUrlList = new ArrayList<>();
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201301/149/2.jpg");
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201301/149/4.jpg");
        mUrlList.add("http://img1.imgtn.bdimg.com/it/u=963551012,3660149984&fm=214&gp=0.jpg");
        mUrlList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=6959877,824205428&fm=27&gp=0.jpg");
        mUrlList.add("http://t2.hddhhn.com/uploads/tu/201409/036/1.jpg");

        mTv_hint = (TextView) findViewById(R.id.tv_hint);
        mBannerView = (BannerView) findViewById(R.id.BannerView);
        mBannerView.setFragmentManager(getSupportFragmentManager());
        mBannerView.setPictureUrl(mUrlList);
        mBannerView.setCircle(true);
        mBannerView.setDurationFavor(4.0f);
        mBannerView.setInitItem(0);
        mBannerView.setInterpolatorType(BannerInterpolator.ACCELERATE_DECELERATE);
        mBannerView.setPictureLoader(new BannerPictureLoader() {
            @Override
            public void showPicture(Fragment fragment, ImageView pictureView, String pictureUrl) {
                Glide.with(fragment)
                        .load(pictureUrl)
                        .into(pictureView);
            }
        });
        mBannerView.setPictureClickListener(new OnBannerPictureClickListener() {
            @Override
            public void onPictureClick(Fragment fragment, int position, List<String> pictureUrl) {
                Toast.makeText(MainActivity.this,"position:"+position+"pictureUrl"+pictureUrl.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        mBannerView.setOnBannerPageSelectedListener(new OnBannerPageSelectedListener() {
            @Override
            public void onPageSelected(int position, String url) {
                mTv_hint.setText("position"+position);
            }
        });
        mBannerView.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBannerView.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBannerView.stopAutoPlay();
    }
}
