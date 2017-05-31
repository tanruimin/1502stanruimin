package com.erzu.dearbaby.application;

import com.blankj.utilcode.util.Utils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.litepal.LitePalApplication;




public class MyApplication extends LitePalApplication {

    {
        PlatformConfig.setSinaWeibo("3383892678", "fbf0b15e0e2c4d88f2688255f8a9d69e", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1106030997", "2wNCn7IxE3PIz7xF");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        AutoLayoutConifg.getInstance().useDeviceSize();
        UMShareAPI.get(this);
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(getApplicationContext()).setShareConfig(config);

        Utils.init(this);
        ZXingLibrary.initDisplayOpinion(this);
    }
}
