package com.erzu.dearbaby.shoppingcart.model.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class GoodsDaoImp implements GoodsDao {

    private Context context;
    private SQLiteDatabase db;
    private final String TABLE_NAME = "goods";


    public GoodsDaoImp(Context context) {
        this.context = context;
        db = Connector.getDatabase();
    }

    //查询方法
    @Override
    public List<GoodsForCart> queryAll() {

        List<GoodsForCart> carts = DataSupport.findAll(GoodsForCart.class);

        return carts;
    }

    //添加方法
    @Override
    public void insert(List<GoodsForCart> goods) {
        DataSupport.saveAll(goods);
    }

    //删除方法
    @Override
    public boolean delete(int id) {

        int delete = DataSupport.delete(GoodsForCart.class, id);
        if (delete > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean upData(GoodsForCart goods) {

        ContentValues values = new ContentValues();
        values.put("count", goods.getCount());
        int update = DataSupport.updateAll(GoodsForCart.class, values);
        if (update > 0) {
            return true;
        }

        return false;
    }
}
