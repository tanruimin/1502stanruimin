package com.erzu.dearbaby.me.model;

import com.google.gson.Gson;
import com.erzu.dearbaby.me.model.bean.GuessLike;
import com.erzu.dearbaby.me.model.bean.User;
import com.erzu.dearbaby.me.model.url.Url;
import com.erzu.dearbaby.me.model.utils.OkHttpUtils;
import com.erzu.dearbaby.me.presenter.Success;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class SuccessImp implements SuccessJson {
    private List<User.DataBean> data;
    private Success success;
    public void getjson(){
        OkHttpUtils.get(Url.uri, new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String  jsons = response.body().string();
if (jsons!=null) {
    Gson gson = new Gson();
    User user = gson.fromJson(jsons, User.class);
    success.succes(user);
}

            }
        });



}
    public void getGuessLike(){
        OkHttpUtils.get(Url.GUESS_FAVOR, new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String  jsons = response.body().string();
if (jsons!=null){
                Gson gson=new Gson();
              GuessLike guessLike = gson.fromJson(jsons, GuessLike.class);

                success.getGuessLike(guessLike.getSugGoods().get(0).getSkus());

            }}
        });



    }

    @Override
    public void getJSon( final Success success) {
       this.success=success;
        getjson();
    }

    @Override
    public void getGuess() {
getGuessLike();
    }


}
