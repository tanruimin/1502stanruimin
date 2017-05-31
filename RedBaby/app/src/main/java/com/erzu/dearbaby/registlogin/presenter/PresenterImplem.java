package com.erzu.dearbaby.registlogin.presenter;

import com.erzu.dearbaby.registlogin.model.buttonModel.ButtonModel;
import com.erzu.dearbaby.registlogin.model.buttonModel.ButtonImpler;
import com.erzu.dearbaby.registlogin.view.ButtonView;



public class PresenterImplem implements ButtonInterOnClick,Passwordseting {
    private ButtonView buttonView;
    private ButtonModel button;
    public PresenterImplem(ButtonView buttonView){
        this.buttonView=buttonView;
        this.button=new ButtonImpler();
    }

    @Override
    public void onLongin(String username, String password) {
        button.setingpassword(username,password,this);
    }

    @Override
    public void onDestroy() {
    buttonView=null;
    }

    @Override
    public void usernameError() {
        if(buttonView!=null){
            buttonView.usernameError();
        }
    }

    @Override
    public void passwordError() {
        if(buttonView!=null){
            buttonView.passwordError();
        }
    }

    @Override
    public void loginSucceed() {
        if(buttonView!=null){
          buttonView.loginSucceed();
        }
    }
}
