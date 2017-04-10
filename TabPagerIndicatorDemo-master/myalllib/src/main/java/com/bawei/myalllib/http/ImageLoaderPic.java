package com.bawei.myalllib.http;

import android.widget.ImageView;

import com.bawei.myalllib.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by hp on 2017/3/28.
 */

public class ImageLoaderPic {
    public static void LoaderPic(String url, ImageView iv){
        ImageLoader.getInstance().displayImage(url,iv,new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher).build());
    }
}
