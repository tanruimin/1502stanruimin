package com.erzu.dearbaby.shoppingcart.view.views;


import com.erzu.dearbaby.shoppingcart.model.bean.GoodsForCart;

import java.util.List;


public interface ViewDao {


    //查询
    List<GoodsForCart> queryAllGoods();

    void insertGoods(List<GoodsForCart> goods);

    boolean deleteGoods(int id);

    boolean upDataGoods(GoodsForCart goods);

}
