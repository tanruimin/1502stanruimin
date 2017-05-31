package com.erzu.dearbaby.home.view.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.base.BaseFragment;
import com.erzu.dearbaby.home.model.bean.OtherDataBean;
import com.erzu.dearbaby.home.model.utils.OkHttpManager;
import com.erzu.dearbaby.home.view.adapter.HomeRecAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;




public class HomeShowDataFragment extends BaseFragment {
    @BindView(R.id.home_recVi_showData)
    RecyclerView homeRecViShowData;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    Unbinder unbinder;
    private HomeRecAdapter adapter;
    private int lastVisibleItem;
    private String path;
    private boolean isFirst;
    private List<OtherDataBean.DataBean> dataBeen;

    public HomeShowDataFragment(String path, boolean isFirst) {
        this.path = path;
        this.isFirst = isFirst;
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String data = (String) msg.obj;
            Gson gson = new Gson();
            dataBeen = gson.fromJson(data, OtherDataBean.class).getData();

            adapter = new HomeRecAdapter(getContext(), dataBeen,isFirst);
            homeRecViShowData.setAdapter(adapter);
        }
    };

    @Override
    protected View initSelfView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home_showdata, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;

    }


    @Override
    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = OkHttpManager.getSyncString(path);
                Message msg = new Message();
                msg.obj = data;
                handler.sendMessage(msg);
            }
        }).start();


    }

    @Override
    public void initView(View view) {

        initData();

        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        homeRecViShowData.setLayoutManager(manager);
        homeRecViShowData.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        swipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshWidget.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        homeRecViShowData.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem == adapter.getItemCount()) {
                    swipeRefreshWidget.setRefreshing(true);

                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    handler.sendEmptyMessageDelayed(0, 3000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = manager.findLastVisibleItemPosition();
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
