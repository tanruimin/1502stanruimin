package com.erzu.dearbaby.registlogin.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.registlogin.view.views.DivEtidActivity;
import com.erzu.dearbaby.registlogin.view.views.MyButton;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.erzu.dearbaby.R.id.get_phone_check_code_again_register;



public class PhoneRegisterActivityTwo extends Activity implements View.OnClickListener{


    @BindView(R.id.login_title_iv)
    ImageView loginTitleIv;
    @BindView(R.id.title_inpo)
    TextView titleInpo;
    @BindView(R.id.title_relayout)
    RelativeLayout titleRelayout;
    @BindView(R.id.code_sent_notice_tv)
    TextView codeSentNoticeTv;
    @BindView(R.id.check_code_input)
    DivEtidActivity checkCodeInput;
    @BindView(R.id.img_delete2)
    ImageView imgDelete2;
    @BindView(get_phone_check_code_again_register)
    Button getPhoneCheckCodeAgainRegister;
    @BindView(R.id.tv_get_voice_code)
    TextView tvGetVoiceCode;
    @BindView(R.id.get_voice_verifycode_view)
    LinearLayout getVoiceVerifycodeView;
    @BindView(R.id.password)
    DivEtidActivity password;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.ll_login_password_show)
    MyButton llLoginPasswordShow;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private Timer time;
    private int dao = 60;
    private String country;
    private String phone;
    private Handler han = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 0||msg.arg1  == 60) {
                getPhoneCheckCodeAgainRegister.setText("重新获取");
                msg.arg1  = 60;
                time.cancel();

            } else {
                getPhoneCheckCodeAgainRegister.setText( msg.arg1  + "秒");
                getPhoneCheckCodeAgainRegister.setTextColor(Color.parseColor("#918f8f"));
                getPhoneCheckCodeAgainRegister.setEnabled(false);
            }
        }
    };
    private String code;
    private String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
         initdata();
        setingPassWord();
        sendphone();
        SMSSDK.registerEventHandler(eventHandler);
    }


    /**
     * 设置密码显示隐藏的方法
     */
    private void setingPassWord() {
        //设置开关显示所用的图片
        llLoginPasswordShow.setImageRes(R.drawable.icon_display, R.drawable.icon_hidden);
        //设置开关的默认状态    true开启状态
        llLoginPasswordShow.setToggleState(true);
        llLoginPasswordShow.setOnToggleStateListener(new MyButton.OnToggleStateListener() {
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


    /**
     * 设置默认初始化时候标题等信息
     */
    private void initdata() {

        titleInpo.setText("设置密码");
        phone = getIntent().getStringExtra("phone");
        codeSentNoticeTv.setText("短信验证码以发送至" + phone);
        getPhoneCheckCodeAgainRegister.setOnClickListener(this);
        btnOk.setOnClickListener(this);

    }
    public void sendphone(){
        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                --dao;
                Message message = new Message();
                message.arg1=dao;
                han.sendMessage(message);
            }
        },0,1000);
        Toast.makeText(this, "发送成功:" + phone, Toast.LENGTH_SHORT).show();
        SMSSDK.getVerificationCode("+86", phone);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case get_phone_check_code_again_register:
                sendphone();
                break;
            case R.id.btn_ok:
                pass = password.getText().toString().trim();
                code = checkCodeInput.getText().toString().trim();
               if(TextUtils.isEmpty(pass)||TextUtils.isEmpty(code)){
                   ToastUtils.showLongToast("验证码或者密码为空");
               }else if(code.length()==4&& pass.length()>6&& pass.length()<18){
                   SMSSDK.submitVerificationCode("+86",phone,code);//国家号，手机号码，验证码
            }else{
                   ToastUtils.showLongToast("验证码或者密码长度不对");
               }


                break;
            case R.id.login_title_iv:
                finish();
                break;
        }
    }
    private void showDailog(String text) {
        AlertDialog dol = new AlertDialog.Builder(this)
                .setTitle(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                }).create();
        dol.show();
    }
    private EventHandler eventHandler = new EventHandler(){

        private String phone2;
        @Override
        public void afterEvent(int i, int i1, Object o) {
            //提交验证码成功,如果验证成功会在data里返回数据。data数据类型为HashMap
            if (i1 == SMSSDK.RESULT_COMPLETE) {
                if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    HashMap<String, Object> mData = (HashMap<String, Object>)o;
                    String country = (String) mData.get("country");//返回的国家编号
                    //返回用户注册的手机号
                    phone2 = (String) mData.get("phone");

                    Log.e("TAG", country + "====" + phone2);
                    if (phone.equals(phone2)){
                        runOnUiThread(new Runnable() {//更改ui的操作要放在主线程，实际可以发送hander
                            @Override
                            public void run() {
                    startActivity(new Intent(PhoneRegisterActivityTwo.this,RegistrationSuccessful.class));
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               ToastUtils.showLongToast("验证码不正确");
                                //     Toast.makeText(MainActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } else if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//获取验证码成功

                } else if (i == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表

                }
            } else {
                ((Throwable) o).printStackTrace();
            }
        }
    };
}
