package com.erzu.dearbaby.me.presenter;

import android.os.Handler;

import com.erzu.dearbaby.me.model.SuccessImp;
import com.erzu.dearbaby.me.model.SuccessJson;
import com.erzu.dearbaby.me.model.bean.GuessLike;
import com.erzu.dearbaby.me.model.bean.User;
import com.erzu.dearbaby.me.view.Shuju;
import com.erzu.dearbaby.me.view.fragment.MineFragment;

import java.util.List;



public class PreImp implements Success {

      private  Shuju shuju;
private SuccessJson successJson;
    public PreImp(Shuju shuju) {
        this.shuju = shuju;
        successJson=new SuccessImp();
        successJson.getJSon(this);
    }

    @Override
    public void succes(final User data) {
        if(data!=null){
            new Handler(((MineFragment)shuju).getActivity().getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    shuju.result(data);
successJson.getGuess();
                }
            }) ;



        }
    }


    @Override
    public void getGuessLike(final List<GuessLike.SugGoodsBean.SkusBean> skusList) {
        new Handler(((MineFragment)shuju).getActivity().getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                shuju.getGuessLike(skusList);

            }
        }) ;

    }
}
