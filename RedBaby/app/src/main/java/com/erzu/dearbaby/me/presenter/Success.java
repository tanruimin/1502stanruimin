package com.erzu.dearbaby.me.presenter;

import com.erzu.dearbaby.me.model.bean.GuessLike;
import com.erzu.dearbaby.me.model.bean.User;

import java.util.List;



public interface Success {
    void succes(User data);
    void getGuessLike(List<GuessLike.SugGoodsBean.SkusBean> skusList);
}
