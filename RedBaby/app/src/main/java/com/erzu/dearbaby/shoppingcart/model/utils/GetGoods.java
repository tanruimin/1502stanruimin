package com.erzu.dearbaby.shoppingcart.model.utils;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.erzu.dearbaby.home.model.bean.Urls;
import com.erzu.dearbaby.home.model.utils.OkHttpManager;
import com.erzu.dearbaby.shoppingcart.model.bean.GoodsBean;

import java.util.List;




public class GetGoods extends Thread {

    private Handler handler;

    public GetGoods(Handler handler) {

        this.handler = handler;

    }

    /**
     * sugGoods为所需数据，只需在handler中获取即可
     */
    public void getGoodData() {
        String data = OkHttpManager.getSyncString(Urls.TUI_JIAN);
        Gson gson = new Gson();
        List<GoodsBean.SugGoodsBean> sugGoods = gson.fromJson(data, GoodsBean.class).getSugGoods();
        Message msg = new Message();
        msg.obj = sugGoods;
        msg.what = 1;
        handler.sendMessage(msg);
    }

    @Override
    public void run() {
        super.run();

        getGoodData();
    }


}
