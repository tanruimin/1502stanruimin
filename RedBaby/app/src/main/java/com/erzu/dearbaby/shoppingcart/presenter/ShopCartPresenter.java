package com.erzu.dearbaby.shoppingcart.presenter;

import com.erzu.dearbaby.shoppingcart.model.bean.GoodsForCart;

import java.util.List;


public interface ShopCartPresenter {


    //查询
    List<GoodsForCart> queryAll();

    void insert(List<GoodsForCart> goods);

    boolean delete(int id);

    boolean upData(GoodsForCart goods);


}
