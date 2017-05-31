package com.erzu.dearbaby.home.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.base.BaseFragment;
import com.erzu.dearbaby.home.model.bean.Urls;
import com.erzu.dearbaby.home.presenter.HomePresenter;
import com.erzu.dearbaby.home.presenter.HomePresenterImp;
import com.erzu.dearbaby.home.view.activity.Class_Edits;
import com.erzu.dearbaby.home.view.activity.HomeWebActivity;
import com.erzu.dearbaby.home.view.adapter.HomeVpAdapter;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends BaseFragment {


    private static final int REQUEST_CODE = 5;
    @BindView(R.id.home_title_btn_barcode)
    ImageView homeTitleBtnBarcode;
    @BindView(R.id.home_search_title_logo)
    ImageView homeSearchTitleLogo;
    @BindView(R.id.text_hint02)
    TextView textHint02;
    @BindView(R.id.home_btn_search_layout)
    RelativeLayout homeBtnSearchLayout;
    @BindView(R.id.msg_unread_count_tv)
    ImageView msgUnreadCountTv;
    @BindView(R.id.message_icon)
    FrameLayout messageIcon;
    @BindView(R.id.home_title_tab)
    TabLayout homeTitleTab;
    @BindView(R.id.djh_one_menu_rl)
    RelativeLayout djhOneMenuRl;
    @BindView(R.id.rob_cate_viewpager)
    ViewPager robCateViewpager;
    @BindView(R.id.move_to_top_btn)
    ImageView moveToTopBtn;
    Unbinder unbinder;
    private HomePresenter homePresenter;
    private List<String> titles;
    private List<Fragment> fragList;

    @Override
    protected View initSelfView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_pull_to_refresh_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void initData() {
        titles = homePresenter.setTitle();
        fragList = new ArrayList<>();
        fragList.add(new HomeShowDataFragment(Urls.PATH_ZHINIAOKU, true));
        fragList.add(new HomeShowDataFragment(Urls.PATH_NAIFEN, false));
        fragList.add(new HomeShowDataFragment(Urls.PATH_XIHUNWEIYANG, false));
        fragList.add(new HomeShowDataFragment(Urls.PATH_WANJU, false));
        fragList.add(new HomeShowDataFragment(Urls.PATH_ZHINIAOKU, false));
        fragList.add(new HomeShowDataFragment(Urls.PATH_NAIFEN, false));
        fragList.add(new HomeShowDataFragment(Urls.PATH_XIHUNWEIYANG, false));
        fragList.add(new HomeShowDataFragment(Urls.PATH_WANJU, false));

    }

    @Override
    public void initView(View view) {

        homePresenter = new HomePresenterImp();
        initData();
        robCateViewpager.setAdapter(new HomeVpAdapter(getChildFragmentManager(), titles, getContext(), fragList));
        homeTitleTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        homeTitleTab.setupWithViewPager(robCateViewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.home_title_btn_barcode, R.id.message_icon, R.id.home_btn_search_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_title_btn_barcode:

                Intent intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.message_icon:

                ToastUtils.showShortToast("消息");
                break;
            case R.id.home_btn_search_layout:

                startActivity(new Intent(getContext(), Class_Edits.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtils.showShortToast("结果为" + result);
                    Intent intent = new Intent(getContext(), HomeWebActivity.class);
                    intent.putExtra("web", result);
                    startActivity(intent);

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtils.showShortToast("解析二维码失败");
                }
            }
        }

    }
}
