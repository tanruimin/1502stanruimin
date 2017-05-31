package com.erzu.dearbaby.registlogin.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.registlogin.view.views.DivEtidActivity;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ForgetPasswordActivity extends Activity implements SeekBar.OnSeekBarChangeListener,View.OnClickListener{

    @BindView(R.id.login_title_iv)
    ImageView loginTitleIv;
    @BindView(R.id.title_inpo)
    TextView titleInpo;
    @BindView(R.id.forget_et)
    DivEtidActivity forgetEt;
    @BindView(R.id.layout_forget_account)
    LinearLayout layoutForgetAccount;
    @BindView(R.id.sb)
    SeekBar sb;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.forgetpassword)
    Button forgetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        ButterKnife.bind(this);
        sb.setOnSeekBarChangeListener(this);
        titleInpo.setText("重置密码");
        forgetEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    forgetpassword.setEnabled(false);
                }else{
                    forgetpassword.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * seekBar进度变化时回调
     *
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getProgress() == seekBar.getMax()) {
            tv.setVisibility(View.VISIBLE);
            tv.setTextColor(Color.WHITE);
            tv.setText("验证通过");
        } else {
            tv.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * seekBar开始触摸时回调
     *
     * @param seekBar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /**
     * seekBar停止触摸时回调
     *
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getProgress() != seekBar.getMax()) {
            seekBar.setProgress(0);
            tv.setVisibility(View.VISIBLE);
            tv.setTextColor(Color.GRAY);
            tv.setText("请按住滑块，拖动到最右边");
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_title_iv:
                finish();
                break;
        }
    }
}