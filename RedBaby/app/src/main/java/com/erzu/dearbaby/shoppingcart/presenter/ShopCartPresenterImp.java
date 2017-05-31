package com.erzu.dearbaby.shoppingcart.presenter;


import android.content.Context;

import com.erzu.dearbaby.shoppingcart.model.bean.GoodsDao;
import com.erzu.dearbaby.shoppingcart.model.bean.GoodsDaoImp;
import com.erzu.dearbaby.shoppingcart.model.bean.GoodsForCart;

import java.util.List;



public class ShopCartPresenterImp implements ShopCartPresenter {


    private GoodsDao dao;


    public ShopCartPresenterImp(Context context) {
        dao = new GoodsDaoImp(context);

    }

    @Override
    public List<GoodsForCart> queryAll() {
        List<GoodsForCart> carts = dao.queryAll();
        return carts;
    }

    @Override
    public void insert(List<GoodsForCart> goods) {

        dao.insert(goods);
    }

    @Override
    public boolean delete(int id) {

        boolean delete = dao.delete(id);
        return delete;
    }

    @Override
    public boolean upData(GoodsForCart goods) {
        boolean upData = dao.upData(goods);
        return upData;
    }


}
