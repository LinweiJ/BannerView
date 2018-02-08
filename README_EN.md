# BannerView

Implementation of FrameLayout+ViewPager+FragmentPagerAdapter for Android that supports a circle banner show a group picture

Feature

- A useful BannerView,it's easy to use just like FrameLayout
- use ViewPager+FragmentPagerAdapter , so slipping smoothness
- customizable picture loader,you can use Glide,Picasso,Fresco,UIL
- other customizable properties

[![](https://jitpack.io/v/LinweiJ/BannerView.svg)](https://jitpack.io/#LinweiJ/BannerView)

![BannerView.gif](https://github.com/LinweiJ/BannerView/blob/master/screen_shot/BannerView.gif)

## Document

- [English](https://github.com/LinweiJ/BannerView/blob/master/README_EN.md)
- [中文](https://github.com/LinweiJ/BannerView/blob/master/README.md)

## Usage

### Gradle

#### 1.Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}

```

#### 2.Add the dependency in your module build.gradle :

```
dependencies {
	        compile 'com.github.LinweiJ:BannerView:0.0.1'
	}
```



### Java

#### 1.simple example

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



#### 2.class/method

- BannerView

| method                                   | description                              | mark (whether must)                 |
| ---------------------------------------- | ---------------------------------------- | :---------------------------------- |
| setPictureUrl(ArrayList<String> pictureUrl) | ArrayList ,a group picture's url         | must                                |
| setFragmentManager(FragmentManager fm)   | v4.app.FragmentManager                   | must                                |
| setCircle(boolean isCircle)              | is circle,true or false                  |                                     |
| setDurationFavor(float durationFavor)    | change the default duration  of srcoler，default1.0F， more bigger more longer |                                     |
| setInterpolatorType(int type)            | Banner Scroller's Interpolator Type,see BannerInterpolator[1] |                                     |
| setInitItem(int position )               | set first show picture,default 0         |                                     |
| initPictureLoader(BannerPictureLoader loader) | init PictureLoader,set success only once,see BannerPictureLoader[2] | must set,without setPictureLoader,  |
| setPictureLoader(BannerPictureLoader loader) | set PictureLoader,see BannerPictureLoader[2] | must set,without initPictureLoadfer |
| setPictureClickListener(OnBannerPictureClickListener listener) | set PictureClickListener,see OnBannerPictureClickListener[3] |                                     |
| addOnPageChangeListener(ViewPager.OnPageChangeListener listener) | ViewPager.OnPageChangeListener           |                                     |
| setOnBannerPageSelectedListener(OnBannerPageSelectedListener listener) | ViewPager Page Selected,see OnBannerPageSelectedListener[4] |                                     |
| start()                                  | init                                     | must                                |
| startAutoPlay()                          | when setCircle(ture), write in Activity/Fragment start(),auto circle |                                     |
| stopAutoPlay()                           | when setCircle(ture), write in Activity/Fragment stop(),stop circle |                                     |
| getViewPager()                           | get this banner ViewPager                |                                     |

- BannerInterpolator[1]

| constant              | description         |
| --------------------- | ------------------- |
| LINEAR                | linear              |
| ACCELERATE            | slow -quick         |
| DECELERATE            | quick - slow        |
| ACCELERATE_DECELERATE | slow - quick - slow |

- BannerPictureLoader[2]

| method                                   | description                              | remark                        |
| ---------------------------------------- | ---------------------------------------- | ----------------------------- |
| showPicture(Fragment fragment, ImageView pictureView, String pictureUrl) | choose image loading library that you like | like Glide,Picasso,Fresco,UIL |

- OnBannerPictureClickListener[3]

| method                                   | description                              | remark                          |
| ---------------------------------------- | ---------------------------------------- | ------------------------------- |
| onPictureClick(Fragment fragment, int position, List<String> pictureUrl) | click on BannerView picture , parameter:pictureUrl:mPictureUrl,position:real  index in pictureUrls | show picture biger or go to URL |

- OnBannerPageSelectedListener[4]

| method                                  | description                              | remark                                   |
| --------------------------------------- | ---------------------------------------- | ---------------------------------------- |
| onPageSelected(int position,String url) | BannerView PageChangeListener,real position in pictureUrls | when setCircle(ture),viewPager.OnPageChangeListener    didn't work |

## more usage

For a working implementation of this project see the demo/  folder.

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
## More

Why not give a star ? (>_@)

