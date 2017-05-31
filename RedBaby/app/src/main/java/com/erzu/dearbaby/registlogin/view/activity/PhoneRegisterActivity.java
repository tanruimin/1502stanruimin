package com.erzu.dearbaby.registlogin.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.registlogin.view.views.DivEtidActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.erzu.dearbaby.R.id.phonr_btn_ok;



public class PhoneRegisterActivity extends Activity implements View.OnClickListener, TextWatcher, View.OnTouchListener {


    @BindView(R.id.login_title_iv)
    ImageView loginTitleIv;
    @BindView(R.id.title_inpo)
    TextView titleInpo;
    @BindView(R.id.phone)
    DivEtidActivity phone;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.pic_verify_code_et)
    DivEtidActivity picVerifyCodeEt;
    @BindView(R.id.img_delete_check_code)
    ImageView imgDeleteCheckCode;
    @BindView(R.id.img_verified)
    ImageView imgVerified;
    @BindView(R.id.get_img_check_again)
    ImageView getImgCheckAgain;
    @BindView(R.id.verification_code_layout)
    LinearLayout verificationCodeLayout;
    @BindView(R.id.rule_checkbox)
    CheckBox ruleCheckbox;
    @BindView(R.id.linksuning)
    TextView linksuning;
    @BindView(R.id.protocol_01)
    TextView protocol01;
    @BindView(R.id.protocol_02)
    TextView protocol02;
    @BindView(R.id.protocol_03)
    TextView protocol03;
    @BindView(R.id.phonr_btn_ok)
    Button phonrBtnOk;
    @BindView(R.id.tv_company_registe)
    TextView tvCompanyRegiste;

    @BindView(R.id.this_linear)
    LinearLayout thisLinear;
    private PopupWindow popupWindow;
    private ImageView webdimiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        ButterKnife.bind(this);
        initOnClick();

    }

    private void initOnClick() {
        titleInpo.setText("验证手机号");
        loginTitleIv.setOnClickListener(this);
        phonrBtnOk.setOnClickListener(this);
        phone.addTextChangedListener(this);
        protocol01.setOnClickListener(this);
        protocol03.setOnClickListener(this);
        loginTitleIv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_title_iv:
                finish();
                break;
            case phonr_btn_ok:
                if (RegexUtils.isMobileExact(phone.getText().toString().trim())) {
                    Intent intent = new Intent(PhoneRegisterActivity.this,PhoneRegisterActivityTwo.class);
                    intent.putExtra("phone",phone.getText().toString().trim());
                    startActivity(intent);
                } else {
                    ToastUtils.showLongToast("手机号格式不正确");
                }
                break;
            case R.id.protocol_01:
                showpopupwindow("file:///android_asset/register_user_rule.html");
                popupWindow.showAtLocation(thisLinear, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.protocol_03:
                showpopupwindow("file:///android_asset/register_yifubao_rule.html");
                popupWindow.showAtLocation(thisLinear, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_company_registe:
                startActivity(new Intent(PhoneRegisterActivity.this,EnterpriseActivity.class));
                break;
        }
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() >= 11) {

            phonrBtnOk.setEnabled(true);

        } else {
            phonrBtnOk.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void showpopupwindow(String uri) {
        View view = View.inflate(this, R.layout.activity_popupwindow, null);
        WebView webView = (WebView) view.findViewById(R.id.activity_web);
        webdimiss = (ImageView) view.findViewById(R.id.web_dimiss);
        webdimiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        webView.loadUrl(uri);
        // imageView.setBackgroundResource(R.mipmap.ic_launcher);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setBackgroundDrawable();
        popupWindow.setAnimationStyle(R.style.popwindow_anim_style);//设置动画

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        showpopupwindow("file:///android_asset/register_user_rule.html");
        popupWindow.showAtLocation(loginTitleIv, Gravity.BOTTOM, 0, 0);
        return true;
    }
}
