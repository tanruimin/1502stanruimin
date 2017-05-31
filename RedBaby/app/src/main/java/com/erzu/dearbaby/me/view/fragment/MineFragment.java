package com.erzu.dearbaby.me.view.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.base.BaseFragment;
import com.erzu.dearbaby.me.model.bean.GuessLike;
import com.erzu.dearbaby.me.model.bean.User;
import com.erzu.dearbaby.me.presenter.PreImp;
import com.erzu.dearbaby.me.view.Shuju;
import com.erzu.dearbaby.me.view.adapter.MyRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MineFragment extends BaseFragment implements Shuju {

    private RecyclerView recyclerView;
private List mybuyList;
    private MyRecyclerAdapter adapter;

    @Override
    protected View initSelfView(LayoutInflater inflater, ViewGroup container) {
        //填充视图
        View v=inflater.inflate(R.layout.loginsuccess,container,false);


        return v;
    }

    @Override
    public void initData() {
        PreImp imp=new PreImp(this);
        mybuyList=new ArrayList();
        for (int i = 0; i <3 ; i++) {
mybuyList.add("string");
        }
        adapter = new MyRecyclerAdapter(getActivity(),mybuyList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initView(View view) {
        //获得资源id
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        //获得模式
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position>4?1:2;
            }
        });
        recyclerView.setLayoutManager(manager);


    }

    @Override
    public void result(User  data) {
        mybuyList.add(data);
        mybuyList.add("string");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getGuessLike(List<GuessLike.SugGoodsBean.SkusBean> list) {
        mybuyList.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
