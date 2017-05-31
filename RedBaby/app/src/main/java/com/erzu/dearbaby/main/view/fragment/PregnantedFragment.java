package com.erzu.dearbaby.main.view.fragment;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.base.BaseFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class PregnantedFragment extends BaseFragment {
    @BindView(R.id.iv_gather_title_back)
    ImageView ivGatherTitleBack;
    @BindView(R.id.tv_gather_title)
    TextView tvGatherTitle;
    @BindView(R.id.iv_gather_pregnanted_light)
    ImageView ivGatherPregnantedLight;
    @BindView(R.id.fl_gather_pregnanted)
    FrameLayout flGatherPregnanted;
    @BindView(R.id.et_gather_pregnanted_date)
    EditText etGatherPregnantedDate;
    @BindView(R.id.rl_gather_pregnanted_date)
    RelativeLayout rlGatherPregnantedDate;
    @BindView(R.id.btn_gather_confirm)
    Button btnGatherConfirm;
    @BindView(R.id.rl_gather_pregnanted)
    RelativeLayout rlGatherPregnanted;
private FragmentManager fm;
    private RotateAnimation animation;
    @Override
    protected View initSelfView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.guide_gather_info_pregnanted, container, false);
        ButterKnife.bind(this, view);
        fm=getActivity().getSupportFragmentManager();
        animation = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.voice_rotate);
        return view;
    }

    @Override
    public void initData() {
ivGatherPregnantedLight.startAnimation(animation);
    }

    @Override
    public void initView(View view) {

        etGatherPregnantedDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            etGatherPregnantedDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });

        etGatherPregnantedDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        etGatherPregnantedDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        btnGatherConfirm.setEnabled(true);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    @OnClick({R.id.iv_gather_title_back, R.id.btn_gather_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_gather_title_back:
                fm.popBackStackImmediate();
                break;
            case R.id.btn_gather_confirm:
                fm.beginTransaction().add(R.id.guideGather,new SelectSuccessFragment()).addToBackStack(null).commit();
                break;
        }
    }
}
