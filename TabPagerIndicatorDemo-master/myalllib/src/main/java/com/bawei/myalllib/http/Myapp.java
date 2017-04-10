package com.bawei.myalllib.http;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by hp on 2017/3/27.
 * 初始化imageload
 */

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCacheExtraOptions(600, 600).build();
        ImageLoader.getInstance().init(build);
    }
}
