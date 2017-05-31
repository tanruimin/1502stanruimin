package com.erzu.dearbaby.home.model.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：holmes k
 * 时间：2017.05.11 19:20
 */


public class OkHttpManager {

    /**
     * 静态实例
     */
    private static OkHttpManager sOkHttpManager = new OkHttpManager();

    /**
     * okhttpclient实例
     */
    private OkHttpClient mClient;


    /**
     * 单例模式  获取OkHttpManager实例
     *
     * @return
     */
    public static OkHttpManager getInstance() {
        //饿汉式。线程安全
        return sOkHttpManager;
    }


    /**
     * 构造方法
     */
    private OkHttpManager() {

        mClient = new OkHttpClient();
        /**
         * 在这里直接设置连接超时.读取超时，写入超时
         */
        mClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        mClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
    }

    /**
     * 对外提供的get方法,同步的方式
     */
    public static Response getSync(String url) {

        //通过获取到的实例来调用内部方法
        return sOkHttpManager.inner_getSync(url);
    }

    /**
     * GET方式请求的内部逻辑处理方式，同步的方式
     */
    private Response inner_getSync(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            //同步请求返回的是response对象
            response = mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 对外提供的同步获取String的方法
     *
     * @param url
     * @return
     */
    public static String getSyncString(String url) {
        return sOkHttpManager.inner_getSyncString(url);
    }


    /**
     * 同步方法
     */
    private String inner_getSyncString(String url) {
        String result = null;
        try {
            /**
             * 把取得到的结果转为字符串，这里最好用string()
             */
            result = inner_getSync(url).body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数据回调接口
     */
    public interface DataCallBack {
        //请求失败
        void requestFailure(Request request, IOException e);

        //请求成功
        void requestSuccess(String result) throws Exception;
    }

}
