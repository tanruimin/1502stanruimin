package com.erzu.dearbaby.me.view;

import com.erzu.dearbaby.me.model.bean.GuessLike;
import com.erzu.dearbaby.me.model.bean.User;

import java.util.List;



public interface Shuju {
    void result(User data);
    void getGuessLike(List<GuessLike.SugGoodsBean.SkusBean> list);
}
