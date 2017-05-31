package com.erzu.dearbaby.registlogin.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.registlogin.presenter.PresenterImplem;
import com.erzu.dearbaby.registlogin.view.ButtonView;
import com.erzu.dearbaby.registlogin.view.views.MyButton;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;



public class LoginActivity extends Activity implements ButtonView, View.OnClickListener,TextWatcher{

    @BindView(R.id.login_title_iv)
    ImageView loginTitleIv;
    @BindView(R.id.title_inpo)
    TextView titleInpo;
    @BindView(R.id.input)
    EditText inpt;
    @BindView(R.id.img_delete2)
    ImageView imgDelete2;
    @BindView(R.id.iv_choose_account)
    ImageView ivChooseAccount;
    @BindView(R.id.choose_account)
    LinearLayout chooseAccount;
    @BindView(R.id.layout_logon_account)
    LinearLayout layoutLogonAccount;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.mybnt)
    MyButton mybnt;
    @BindView(R.id.check_code_input)
    EditText checkCodeInput;
    @BindView(R.id.img_delete_check_code)
    ImageView imgDeleteCheckCode;
    @BindView(R.id.img_verified)
    ImageView imgVerified;
    @BindView(R.id.get_img_check_again)
    ImageView getImgCheckAgain;
    @BindView(R.id.verification_code_layout)
    LinearLayout verificationCodeLayout;
    @BindView(R.id.btn_logon)
    Button btnLogon;
    @BindView(R.id.tv_forgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.login_weixin)
    RadioButton loginWeixin;
    @BindView(R.id.login_xinlang)
    RadioButton loginXinlang;
    @BindView(R.id.ralayout)
    RelativeLayout ralayout;
    private PresenterImplem presenterImplem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        ButterKnife.bind(this);
        initview();

        inpt.addTextChangedListener(this);
        checkCodeInput.addTextChangedListener(this);
    }

    private void initview() {
        ButterKnife.bind(this);
        btnLogon.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        presenterImplem = new PresenterImplem(this);
        loginXinlang.setOnClickListener(this);
        loginWeixin.setOnClickListener(this);

        //设置开关显示所用的图片
        mybnt.setImageRes(R.drawable.icon_display,R.drawable.icon_hidden);
        //设置开关的默认状态    true开启状态
        mybnt.setToggleState(true);
        mybnt.setOnToggleStateListener(new MyButton.OnToggleStateListener() {
            @Override
            public void onToggleState(boolean state) {
                if (state) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    @Override
    public void usernameError() {
        Toast.makeText(this, "登录账户错误", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void passwordError() {
        Toast.makeText(this, "登录密码错误", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void loginSucceed() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logon:
                if(RegexUtils.isUsername(inpt.getText().toString().trim())){
                    if(password.getText().toString().trim().length()>6&&password.getText().toString().trim().length()<18){
                        presenterImplem.onLongin(inpt.getText().toString(), password.getText().toString());
                    }else{
                        ToastUtils.showLongToast("密码长度");
                    }
                }else{
                    ToastUtils.showLongToast("用户名长度必须在6-20位或不能以—开头");
                }

                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this, PhoneRegisterActivity.class));
                break;
            case R.id.tv_forgetPassword:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.login_weixin:
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.login_xinlang:

                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                break;
        }
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(TextUtils.isEmpty(inpt.getText().toString())){
            btnLogon.setEnabled(false);
        }
        else{
            btnLogon.setEnabled(true);
        }
    }
}

