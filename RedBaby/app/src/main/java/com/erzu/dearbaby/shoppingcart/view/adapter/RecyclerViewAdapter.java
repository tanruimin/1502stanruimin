package com.erzu.dearbaby.shoppingcart.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erzu.dearbaby.R;
import com.erzu.dearbaby.shoppingcart.model.bean.GoodsBean;
import com.erzu.dearbaby.shoppingcart.model.bean.GoodsForCart;
import com.erzu.dearbaby.shoppingcart.presenter.ShopCartPresenter;
import com.erzu.dearbaby.shoppingcart.presenter.ShopCartPresenterImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPEONE = 1;
    private static final int TYPETWO = 2;

    private Context context;
    private List<GoodsBean.SugGoodsBean> sugGoods;
    private ShopCartPresenter shopCartPresenter;
    private List<GoodsForCart> carts;

    public RecyclerViewAdapter(Context context, List<GoodsBean.SugGoodsBean> sugGoods) {
        this.context = context;
        this.sugGoods = sugGoods;
        shopCartPresenter = new ShopCartPresenterImp(context);
        carts = shopCartPresenter.queryAll();
    }


    @Override
    public int getItemViewType(int position) {
        int i = position % 2;
        if (i == 0) {
            return TYPEONE;
        } else if (i == 1) {
            return TYPETWO;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPEONE) {
            View view = LayoutInflater.from(context).inflate(R.layout.shopping_item, parent, false);
            Viewholder1 viewholder1 = new Viewholder1(view);
            return viewholder1;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.shopping_item2_recyclerview, parent, false);
            Viewholder2 viewholder2 = new Viewholder2(view);
            return viewholder2;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPEONE) {
            Viewholder1 viewholder1 = (Viewholder1) holder;
            viewholder1.shoppingItemRecyclerview.setLayoutManager(new LinearLayoutManager(context));
            RecyclerView_item1 item1 = new RecyclerView_item1(context, carts);
            viewholder1.shoppingItemRecyclerview.setAdapter(item1);
        } else {
            Viewholder2 viewHolder2 = (Viewholder2) holder;
            viewHolder2.item2Recyclerview.setLayoutManager(new GridLayoutManager(context, 2));
            RecyclerView_item2 item2 = new RecyclerView_item2(context, sugGoods);
            viewHolder2.item2Recyclerview.setAdapter(item2);
            viewHolder2.item2Recyclerview.addItemDecoration(new RecyclerViewDividerItemDecoration(context));

        }


    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class Viewholder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.shopping_item_recyclerview)
        RecyclerView shoppingItemRecyclerview;

        public Viewholder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public class Viewholder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.item2_recyclerview)
        RecyclerView item2Recyclerview;

        public Viewholder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
