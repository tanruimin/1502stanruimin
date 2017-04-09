package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.tanruimin.zhoukao0407.R;

import java.util.ArrayList;

import adapter.leftadapter;
import bean.phone;

/**
 * data:2017/4/9
 * name:tanruimin tanruimin
 * function:
 */

public class fra extends Fragment {
    private ListView mLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leftbuju,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLv = (ListView) getView().findViewById(R.id.frag1_lv);
        //初始化数据
        initdata();

    }

    private void initdata() {
        ArrayList<phone> strings = new ArrayList<phone>();
        strings.add(new phone("搜索",R.mipmap.ic_drawer_search_normal));
        strings.add(new phone("收藏",R.mipmap.ic_drawer_favorite_normal));
        strings.add(new phone("消息",R.mipmap.ic_drawer_message_normal));
        strings.add(new phone("离线",R.mipmap.ic_drawer_offline_normal));
        strings.add(new phone("活动",R.mipmap.left_drawer_activity));
        strings.add(new phone("设置",R.mipmap.ic_drawer_setting_normal));
        strings.add(new phone("反馈",R.mipmap.ic_drawer_feedback_normal));
        strings.add(new phone("精彩应用",R.mipmap.ic_drawer_appstore_normal));
        mLv.setAdapter(new leftadapter(getActivity(),strings));
    }
}

