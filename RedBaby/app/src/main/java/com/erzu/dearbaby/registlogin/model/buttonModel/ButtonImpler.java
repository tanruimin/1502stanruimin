package com.erzu.dearbaby.registlogin.model.buttonModel;

import android.os.Handler;
import android.text.TextUtils;

import com.erzu.dearbaby.registlogin.presenter.ButtonInterOnClick;



public class ButtonImpler implements ButtonModel {

    private  boolean bo;
    @Override
    public void setingpassword(final String username, final String password, final ButtonInterOnClick buttonInterOnClick) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if(TextUtils.isEmpty(username)){
                   buttonInterOnClick.usernameError();
                   bo=true;
               }
                if(TextUtils.isEmpty(password)){
                    buttonInterOnClick.passwordError();
                    bo=false;
                }
                if(!bo){
                    buttonInterOnClick.loginSucceed();
                }
            }
        },1000);
    }
}
