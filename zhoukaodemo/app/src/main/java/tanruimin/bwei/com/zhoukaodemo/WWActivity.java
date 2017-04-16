package tanruimin.bwei.com.zhoukaodemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import bean.GsonBean;

/**
 * data:2017/4/14
 * name:tanruimin tanruimin
 * function:
 */
public class WWActivity extends SlidingFragmentActivity {
    public static final String url="http://appapi.kangzhi.com/app/kzdrugs/index?uid=1476860172&catid=%E8%A5%BF%E8%8D%AF&sort=default&page=1";
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String data= (String) msg.obj;
            Gson gson = new Gson();
            GsonBean dataBean = gson.fromJson(data, GsonBean.class);
            List<GsonBean.DataBean> data1 = dataBean.getData();
            mWdithtList.setAdapter(new Adapter<GsonBean.DataBean>(WWActivity.this,data1) {
                @Override
                public void convert(ViewHolder holder, GsonBean.DataBean item) {
                    holder.setText(R.id.textView,item.getTitle());
                    holder.setText(R.id.textView2,item.getApply());
                    holder.setText(R.id.textView3,item.getBuy_price());
                }
            });
        }
    };
    private ListView mWdithtList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ww);
        initView();
        initSlidingmenu();
        initData();
    }
    public void initSlidingmenu(){
        Fragment leftMenuFragment = new LeftFragment();
        setBehindContentView(R.layout.left_menu_frame);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_left_menu_frame, leftMenuFragment).commit();
        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        // menu.setBehindScrollScale(1.0f);
        menu.setSecondaryShadowDrawable(R.drawable.shadow);
        //设置右边（二级）侧滑菜单
    }
    public void showLeftMenu(View view)
    {
        getSlidingMenu().showMenu();
    }
    private void initView() {
        mWdithtList = (ListView) findViewById(R.id.weight_listview);
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
