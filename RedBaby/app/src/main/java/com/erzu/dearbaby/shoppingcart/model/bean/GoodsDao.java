package com.erzu.dearbaby.shoppingcart.model.bean;

import java.util.List;


public interface GoodsDao {


    //查询
    List<GoodsForCart> queryAll();

    void insert(List<GoodsForCart> goods);

    boolean delete(int id);

    boolean upData(GoodsForCart goods);
}
