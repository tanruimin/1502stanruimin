package com.bawei.myalllib.http;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by hp on 2017/3/27.
 * httpurlconnection解析工具
 */

public class HttpUtils extends Thread{
    //构造方法传入参数

    private String url;
    private Handler handler;
    private HttpURLConnection connect;

    public HttpUtils(String url, Handler handler) {
        this.url = url;
        this.handler = handler;
    }

//run方法中向handler发送解析到的数据

    @Override
    public void run() {
        super.run();
        String json = doGet();
        if(json!=null){
        if (json.equals("gg")){
            Message msg = new Message();
            msg.what=2;
            msg.obj="网络没有连接成功";
            handler.sendMessage(msg);
        }else{
            Message message = new Message();
            message.what=1;
            message.obj=json;
            handler.sendMessage(message);
        }}
    }

//从网络解析json串

    private String doGet(){
        try {
            URL path = new URL(url);
            connect = (HttpURLConnection) path.openConnection();
            connect.setRequestMethod("GET");
            connect.setReadTimeout(8000);
            connect.setConnectTimeout(8000);
            if (connect.getResponseCode()==200){
                InputStream inputStream = connect.getInputStream();
                String json = StreamtoStr(inputStream);
                return json;
            }else {
                return "gg";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connect!= null) {
                connect.disconnect();
            }}

        return "gg";
    }

    //把输入流转化为字符串

    private String StreamtoStr(InputStream inputStream){
        StringBuffer sb = new StringBuffer();
        String str=null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((str=reader.readLine())!=null){
                sb.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
