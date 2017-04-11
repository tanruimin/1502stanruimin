package shanyao.tabpagerindicatordemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bawei.myalllib.http.MyGsonUtils;

import java.util.List;

import adapter.Mybase;
import bean.Mybean;
import shanyao.tabpagerindicatordemo.R;
import shanyao.tabpagerindicatordemo.utils.MyXutils;

/**
 * Created by shanyao on 2016/6/17.
 */
public class MyFragment extends Fragment {
    public static MyFragment getFragment(String url) {
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpager_indicator,container,false);
    }
    private ListView lv;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            Mybean mybean = (Mybean) new MyGsonUtils(json, Mybean.class).jsonToGson();
            Mybean.ResultBean result = mybean.getResult();
            List<Mybean.ResultBean.DataBean> data = result.getData();
            lv.setAdapter(new Mybase(getActivity(),data));

        }
    };
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv= (ListView) getView().findViewById(R.id.lv);
        Bundle bundle = getArguments();
        String url = bundle.getString("url");
        MyXutils.xutilstostr(url,handler);
    }
}
