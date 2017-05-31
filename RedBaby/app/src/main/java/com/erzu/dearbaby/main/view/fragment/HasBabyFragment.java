package com.erzu.dearbaby.main.view.fragment;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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


public class HasBabyFragment extends BaseFragment {
    @BindView(R.id.iv_gather_title_back)
    ImageView ivGatherTitleBack;
    @BindView(R.id.tv_gather_title)
    TextView tvGatherTitle;
    @BindView(R.id.tv_gather_birthed_prince)
    TextView tvGatherBirthedPrince;
    @BindView(R.id.iv_gather_birthed_prince_bg)
    ImageView ivGatherBirthedPrinceBg;
    @BindView(R.id.iv_gather_birthed_prince_select)
    ImageView ivGatherBirthedPrinceSelect;
    @BindView(R.id.fl_gather_birthed_prince)
    FrameLayout flGatherBirthedPrince;
    @BindView(R.id.tv_gather_birthed_princess)
    TextView tvGatherBirthedPrincess;
    @BindView(R.id.iv_gather_birthed_princess_bg)
    ImageView ivGatherBirthedPrincessBg;
    @BindView(R.id.iv_gather_birthed_princess_select)
    ImageView ivGatherBirthedPrincessSelect;
    @BindView(R.id.fl_gather_birthed_princess)
    FrameLayout flGatherBirthedPrincess;
    @BindView(R.id.et_gather_birthed_birthdate)
    EditText etGatherBirthedBirthdate;
    @BindView(R.id.rl_gather_birthed_birthdate)
    RelativeLayout rlGatherBirthedBirthdate;
    @BindView(R.id.tv_gather_birthed_babyname)
    TextView tvGatherBirthedBabyname;
    @BindView(R.id.et_gather_birthed_babyname)
    EditText etGatherBirthedBabyname;
    @BindView(R.id.rl_gather_birthed_babyname)
    RelativeLayout rlGatherBirthedBabyname;
    @BindView(R.id.btn_gather_confirm)
    Button btnGatherConfirm;
    private FragmentManager fm;
    private RotateAnimation animation;

    @Override
    protected View initSelfView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.guide_gather_info_birthed, container, false);
        ButterKnife.bind(this, view);
        fm = getActivity().getSupportFragmentManager();
        animation = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.voice_rotate);
        return view;
    }

    @Override
    public void initData() {
        ivGatherBirthedPrincessBg.setVisibility(View.INVISIBLE);
        ivGatherBirthedPrinceBg.setVisibility(View.INVISIBLE);
        ivGatherBirthedPrinceSelect.setVisibility(View.INVISIBLE);
        ivGatherBirthedPrincessSelect.setVisibility(View.VISIBLE);
        ivGatherBirthedPrincessBg.startAnimation(animation);

    }

    @Override
    public void initView(View view) {
        etGatherBirthedBabyname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s) || TextUtils.isEmpty(etGatherBirthedBirthdate.getText().toString())) {
                    btnGatherConfirm.setEnabled(false);
                } else {
                    btnGatherConfirm.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etGatherBirthedBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            etGatherBirthedBirthdate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });

        etGatherBirthedBirthdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();

                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        etGatherBirthedBirthdate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        if (!TextUtils.isEmpty(etGatherBirthedBabyname.getText().toString())) {
                            btnGatherConfirm.setEnabled(true);
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }


    @OnClick({R.id.iv_gather_title_back, R.id.btn_gather_confirm,R.id.fl_gather_birthed_prince, R.id.fl_gather_birthed_princess})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_gather_title_back:
                fm.popBackStackImmediate();
                break;
            case R.id.btn_gather_confirm:
                fm.beginTransaction().add(R.id.guideGather, new SelectSuccessFragment()).addToBackStack(null).commit();
                break;
            case R.id.fl_gather_birthed_prince:
                ivGatherBirthedPrincessBg.clearAnimation();
                ivGatherBirthedPrinceBg.startAnimation(animation);

                ivGatherBirthedPrinceSelect.setVisibility(View.VISIBLE);
                ivGatherBirthedPrincessSelect.setVisibility(View.INVISIBLE);

                break;
            case R.id.fl_gather_birthed_princess:
                ivGatherBirthedPrinceBg.clearAnimation();
                ivGatherBirthedPrincessBg.startAnimation(animation);
                ivGatherBirthedPrinceSelect.setVisibility(View.INVISIBLE);
                ivGatherBirthedPrincessSelect.setVisibility(View.VISIBLE);
                break;
        }
    }



}
