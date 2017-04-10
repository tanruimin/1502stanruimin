package shanyao.tabpagerindicatordemo.utils;


import org.xutils.image.ImageOptions;

import shanyao.tabpagerindicatordemo.R;



public class MyImageOptions {

    public static ImageOptions options(){
        ImageOptions builder = new ImageOptions.Builder()
                .setFadeIn(true)//淡入效果
//ImageOptions.Builder()的一些其他属性：
//                .setCircular(true) //设置图片显示为圆形
                .setSquare(true) //设置图片显示为正方形
                .setCrop(true).setSize(200,200) //设置大小
//                .setAnimation(animation) //设置动画
//                .setFailureDrawable(Drawable failureDrawable) //设置加载失败的动画
                .setFailureDrawableId(R.mipmap.ic_launcher) //以资源id设置加载失败的动画
//                .setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
//                .setLoadingDrawableId(int loadingDrawable) //以资源id设置加载中的动画
                 .setIgnoreGif(false) //忽略Gif图片
//                .setParamsBuilder(ParamsBuilder paramsBuilder) //在网络请求中添加一些参数
//                .setRaduis(int raduis) //设置拐角弧度
                .setUseMemCache(true).build();//设置使用MemCache，默认true;
        return builder;

    }
}
