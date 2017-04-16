package tanruimin.bwei.com.zhoukaodemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bean.yaoBean;

/**
 * data:2017/4/14
 * name:tanruimin tanruimin
 * function:
 */

public class LeftFragment extends Fragment {
    private ListView mListView;
    private List<String> mList=new ArrayList<>();
    public static final String url="http://appapi.kangzhi.com/app/kzdrugs/drugs_cate?uid=";
    private Handler mHandler=new Handler(){
           @Override
           public void handleMessage(Message msg) {
               super.handleMessage(msg);
              String data= (String) msg.obj;
               Log.d("zzz",data);
               Gson gson = new Gson();
               yaoBean medicineBean = gson.fromJson(data, yaoBean.class);
               List<yaoBean.DataBean> data1 = medicineBean.getData();
               for (yaoBean.DataBean bean:data1) {
                   mList.add(bean.getName());
               }
               mListView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,mList));
           }
       };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leftmenu,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();


    }
    private void initData() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               String s = doGet(url);
               Message message = Message.obtain();
               message.obj=s;
               mHandler.sendMessage(message);
           }
       }).start();


    }

    private void initView() {
        mListView = (ListView)getView().findViewById(R.id.left_listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                    case 1:
                        Intent intent1 = new Intent(getActivity(), CCActivity.class);
                        startActivity(intent1);
                    case 2:
                        Intent intent2 = new Intent(getActivity(), WWActivity.class);
                        startActivity(intent2);
                }
            }
        });

    }
    public String doGet(String uri){
            StringBuffer sb=new StringBuffer();
            try {
                URL url=new URL(uri);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                if(conn.getResponseCode()==200){
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String line=null;
                    while((line=br.readLine())!=null){
                        sb.append(line);
                    }
                }
                return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
}
