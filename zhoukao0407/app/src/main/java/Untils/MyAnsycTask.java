package Untils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import bean.Bean;
import bean.myadapter;

/**
 * data:2017/4/7
 * name:tanruimin tanruimin
 * function:
 */



public class MyAnsycTask extends AsyncTask<String,Integer,String> {

    //把需要的东西用构造函数传过来
    public MyAnsycTask(Context context, ListView lv) {
        this.context = context;
        this.lv = lv;
    }

    private ListView lv;
    private Context context;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //更新UI
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Gson gson = new Gson();
        Bean bean_1 = gson.fromJson(s, Bean.class);
        final List<Bean.ListBean> data = bean_1.getList();
        myadapter adapter = new myadapter(context, data);
        lv.setAdapter(adapter);
        //设置点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,data.get(position).getId()+"",Toast.LENGTH_SHORT).show();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long
                    id) {
                Bean.ListBean listBean = data.get(position);
                data.remove(listBean);
                myadapter adapter = new myadapter(context, data);
                lv.setAdapter(adapter);
                return true;
            }
        });
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    //耗时操作
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection httpURLConnection=null;
        try {
            //得到路径
            URL url1 = new URL(params[0]);
            //获取链接
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            //定义获取请求的方式为get请求
            httpURLConnection.setRequestMethod("POST");
            //定义一个编码集格式
            httpURLConnection.setRequestProperty("encoding","gbk");
            //定义网络链接耗时的时间
            httpURLConnection.setConnectTimeout(5000);
            //定义网络读取耗时的时间
            httpURLConnection.setReadTimeout(5000);
            //获取网路的链接请求码
            int responseCode = httpURLConnection.getResponseCode();
            //对请求码进行判断
            if (responseCode == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
//              BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
//                String len;
                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                byte[] byt=new byte[1024];
                int len=0;
//                while ((len=br.readLine())!=null){
//                    jilu+="/n"+len;
//                }
//                br.close();
                while ((len=inputStream.read(byt))!=-1){
                    boas.write(byt,0,len);
                }
                boas.close();
                return boas.toString("utf-8");
            }else {
                Log.d("zzzz","请求失败！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if ( httpURLConnection!= null) {
                //释放资源！！
                httpURLConnection.disconnect();
            }
        }
        return "解析失败！！！";
    }
}