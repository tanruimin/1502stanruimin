package com.erzu.dearbaby.main.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.base.BaseFragment;
import com.erzu.dearbaby.main.view.activity.MainActivity;
import com.erzu.dearbaby.utils.SharedPreferenceUtils2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class SelectSuccessFragment extends BaseFragment {
    @BindView(R.id.iv_gather_title_back)
    ImageView ivGatherTitleBack;
    @BindView(R.id.tv_gather_title)
    TextView tvGatherTitle;
    @BindView(R.id.btn_gather_confirm)
    Button btnGatherConfirm;

    @Override
    protected View initSelfView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.guide_gather_info_success, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {

    }

    @OnClick({R.id.iv_gather_title_back, R.id.btn_gather_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_gather_title_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_gather_confirm:
                MainActivity.startMainActiviy(getContext());
                SharedPreferenceUtils2.put(getContext(),"hadSetState",true);
                getActivity().finish();
                break;
        }
    }
}
