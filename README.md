# BannerView

一个基于FrameLayout+ViewPager+FragmentPagerAdapter的轮播图

特点:

- 广告轮播图,跟FrameLayout一样简单使用
- 基于ViewPager+FragmentPagerAdapter,滑动流畅
- 可定制图片加载器
- 提供一些定制接口

[![](https://jitpack.io/v/LinweiJ/BannerView.svg)](https://jitpack.io/#LinweiJ/BannerView)

![BannerView.gif](https://github.com/LinweiJ/BannerView/blob/master/screen_shot/BannerView.gif)


## 文档

- [English](https://github.com/LinweiJ/BannerView/blob/master/README_EN.md)
- [中文](https://github.com/LinweiJ/BannerView/blob/master/README.md)

## 如何使用它

### gradle

#### 先在 project的build.gradle 添加:

```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}

```

#### 然后在module的build.gradle 添加:

```
dependencies {
	        compile 'com.github.LinweiJ:BannerView:0.0.1'
	}
```



### JAVA

#### 1.简单使用例子

- xml

```
<com.lvchehui.www.demo_best_adapter.banner.BannerView
		android:id="@+id/BannerView"
		android:layout_width="match_parent"
		android:layout_height="493dp"
		android:layout_weight="1"
		android:background="#cdcdcd"
	>
	 <TextView
		 android:id="@+id/tv_hint"
	     android:layout_width="match_parent"
	     android:layout_height="wrap_content"
         android:layout_gravity="bottom"
         android:text="提示"
     />
</com.lvchehui.www.demo_best_adapter.banner.BannerView>
```

- java

```
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
                ToastUtil.showToast(WBannerActivity.this,"position:"+position+"pictureUrl"+pictureUrl.get(position));
            }
        });
        mBannerView.setOnBannerPageSelectedListener(new OnBannerPageSelectedListener() {
            @Override
            public void onPageSelected(int position, String url) {
                mTv_hint.setText("position"+position+"\n"+url);
            }
        });
        mBannerView.start();
        
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

```



#### 2.类/方法

- BannerView

| 方法                                       | 描述                                       | 备注 (是否设置)                 |
| ---------------------------------------- | ---------------------------------------- | ------------------------- |
| setPictureUrl(ArrayList<String> pictureUrl) | 图片列表                                     | 必须                        |
| setFragmentManager(FragmentManager fm)   | v4.app.FragmentManager                   | 必须                        |
| setCircle(boolean isCircle)              | 是否轮播                                     | 可选                        |
| setDurationFavor(float durationFavor)    | 滑动动画时长因子，默认1.0F，值越大时间越长                  | 可选                        |
| setInterpolatorType(int type)            | 滑动动画变化速度类型,见BannerInterpolator[1]        | 可选                        |
| setInitItem(int position )               | 初始页，默认0                                  | 可选                        |
| initPictureLoader(BannerPictureLoader loader) | 初始化图片加载器(只初始化一次),见BannerPictureLoader[2] | 与setPictureLoader,必须二选一   |
| setPictureLoader(BannerPictureLoader loader) | 设置图片加载器,见BannerPictureLoader[2]          | 与initPictureLoader ,必须二选一 |
| setPictureClickListener(OnBannerPictureClickListener listener) | 设置图片点击事件,见OnBannerPictureClickListener[3] | 可选                        |
| addOnPageChangeListener(ViewPager.OnPageChangeListener listener) | ViewPager 滑动监听事件                         | 可选                        |
| setOnBannerPageSelectedListener(OnBannerPageSelectedListener listener) | ViewPager 页面选中事件,见OnBannerPageSelectedListener[4] | 可选                        |
| start()                                  | 配置完成后调用                                  | 必须                        |
| startAutoPlay()                          | 设置setCircle(ture)有效,通常在start()调用,开始轮播,   | 可选                        |
| stopAutoPlay()                           | 设置setCircle(ture)有效,通常在stop()调用,停止轮播     | 可选                        |
| getViewPager()                           | 获取ViewPager                              | 可选                        |

- BannerInterpolator[1]

| 常量                    | 描述                          |
| --------------------- | --------------------------- |
| LINEAR                | 直线                          |
| ACCELERATE            | 在动画开始的地方速率改变比较慢，然后开始加速      |
| DECELERATE            | 在动画开始的地方快然后慢                |
| ACCELERATE_DECELERATE | 在动画开始与介绍的地方速率改变比较慢，在中间的时候加速 |

- BannerPictureLoader[2]

| 方法                                       | 描述        | 备注            |
| ---------------------------------------- | --------- | ------------- |
| showPicture(Fragment fragment, ImageView pictureView, String pictureUrl) | 自定义图片加载方式 | 可选Glide等图片加载库 |

- OnBannerPictureClickListener[3]

| 方法                                       | 描述                                       | 备注   |
| ---------------------------------------- | ---------------------------------------- | ---- |
| onPictureClick(Fragment fragment, int position, List<String> pictureUrl) | BannerView图片点击事件,参数pictureUrl:图片列表,position:当前点击图片index |      |

- OnBannerPageSelectedListener[4]

| 方法                                      | 描述               | 备注                                       |
| --------------------------------------- | ---------------- | ---------------------------------------- |
| onPageSelected(int position,String url) | BannerView滑动切换事件 | 当设置轮播时,viewPager的OnPageChangeListener不再适用 |

## 更多细节

可以参考 demo/ 示例

## License

```
Copyright 2017 LinWeiJia

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
## 底部

随手给颗星呗 ? (>_@)