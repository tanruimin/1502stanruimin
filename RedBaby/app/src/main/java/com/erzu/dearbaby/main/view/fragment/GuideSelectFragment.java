package com.erzu.dearbaby.main.view.fragment;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GuideSelectFragment extends BaseFragment {


    @BindView(R.id.iv_gather_title_back)
    ImageView ivGatherTitleBack;
    @BindView(R.id.tv_gather_title)
    TextView tvGatherTitle;
    @BindView(R.id.tv_gather_state_select_tip)
    TextView tvGatherStateSelectTip;
    @BindView(R.id.tv_gather_state_select_p1)
    TextView tvGatherStateSelectP1;
    @BindView(R.id.iv_gather_state_select_p1)
    ImageView ivGatherStateSelectP1;
    @BindView(R.id.fl_gather_state_pregnanted)
    FrameLayout flGatherStatePregnanted;
    @BindView(R.id.iv_gather_state_select_p2)
    ImageView ivGatherStateSelectP2;
    @BindView(R.id.fl_gather_state_prepregnant)
    FrameLayout flGatherStatePrepregnant;
    @BindView(R.id.iv_gather_state_select_p3)
    ImageView ivGatherStateSelectP3;
    @BindView(R.id.fl_gather_state_birthed)
    FrameLayout flGatherStateBirthed;
    @BindView(R.id.rl_gather_state_select)
    RelativeLayout rlGatherStateSelect;
private FragmentManager fmSelect;
    @Override
    protected View initSelfView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.guide_gather_info_state_select, container,false);
        ButterKnife.bind(this, view);
        fmSelect=getActivity().getSupportFragmentManager();
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Tag","OnPause");
    }

    @Override
    public void initView(View view) {
ivGatherTitleBack.setVisibility(View.INVISIBLE);
        ivGatherStateSelectP1.setVisibility(View.INVISIBLE);
        ivGatherStateSelectP2.setVisibility(View.INVISIBLE);
        ivGatherStateSelectP3.setVisibility(View.INVISIBLE);
    }


    @OnClick({R.id.fl_gather_state_pregnanted, R.id.fl_gather_state_prepregnant, R.id.fl_gather_state_birthed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_gather_state_pregnanted:
                ivGatherStateSelectP1.setVisibility(View.VISIBLE);
                ivGatherStateSelectP2.setVisibility(View.INVISIBLE);
                ivGatherStateSelectP3.setVisibility(View.INVISIBLE);
                fmSelect.beginTransaction().add(R.id.guideGather,new PregnantedFragment()).addToBackStack(null).commit();
                break;
            case R.id.fl_gather_state_prepregnant:
                ivGatherStateSelectP2.setVisibility(View.VISIBLE);
                ivGatherStateSelectP1.setVisibility(View.INVISIBLE);
                ivGatherStateSelectP3.setVisibility(View.INVISIBLE);
                fmSelect.beginTransaction().add(R.id.guideGather,new SelectSuccessFragment()).addToBackStack(null).commit();

                break;
            case R.id.fl_gather_state_birthed:
                ivGatherStateSelectP3.setVisibility(View.VISIBLE);
                ivGatherStateSelectP2.setVisibility(View.INVISIBLE);
                ivGatherStateSelectP1.setVisibility(View.INVISIBLE);
                fmSelect.beginTransaction().add(R.id.guideGather,new HasBabyFragment()).addToBackStack(null).commit();
                break;
        }
    }
}
