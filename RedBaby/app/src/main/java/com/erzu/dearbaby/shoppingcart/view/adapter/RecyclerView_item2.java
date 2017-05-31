package com.erzu.dearbaby.shoppingcart.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.home.model.bean.Urls;
import com.erzu.dearbaby.shoppingcart.model.bean.GoodsBean;
import com.erzu.dearbaby.shoppingcart.model.bean.GoodsForCart;
import com.erzu.dearbaby.shoppingcart.presenter.ShopCartPresenterImp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RecyclerView_item2 extends RecyclerView.Adapter<RecyclerView_item2.MyViewholder> {


    private List<GoodsBean.SugGoodsBean> sugGoods;
    private Context context;
    private String picPath;
    private final ShopCartPresenterImp presenterImp;

    public RecyclerView_item2(Context context, List<GoodsBean.SugGoodsBean> sugGoods) {
        this.context = context;
        this.sugGoods = sugGoods;
        presenterImp = new ShopCartPresenterImp(context);
    }


    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_shopping_tiem2, parent, false);
        MyViewholder viewholder = new MyViewholder(view);
        return viewholder;
    }

    List<GoodsForCart> list = new ArrayList<GoodsForCart>();

    @Override
    public void onBindViewHolder(MyViewholder holder, final int position) {
        String code = sugGoods.get(0).getSkus().get(position).getSugGoodsCode();
        if (!TextUtils.isEmpty(code)) {
            picPath = Urls.GOODSCAR_PIC_TITLE + code + Urls.GOODSCAR_PIC_FOOT;
            Glide.with(context).load(picPath).into(holder.imageView2);
        }
        holder.jiage.setText(sugGoods.get(0).getSkus().get(position).getPrice());
        holder.textView.setText(sugGoods.get(0).getSkus().get(position).getSugGoodsName());
        holder.textView2.setText(sugGoods.get(0).getSkus().get(position).getSalesVolume() + "人已购买");
        holder.addToCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsForCart cart = new GoodsForCart();

                cart.setPrice(Double.parseDouble(sugGoods.get(0).getSkus().get(position).getPrice()));
                cart.setCount(1);
                cart.setIsChecked(1);
                cart.setPicUrl(picPath);
                cart.setTitle(sugGoods.get(0).getSkus().get(position).getSugGoodsName());
                list.add(cart);
                presenterImp.insert(list);
                list.clear();

            }
        });
    }


    @Override
    public int getItemCount() {
        return sugGoods != null ? sugGoods.get(0).getSkus().size() : 0;
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView2)
        ImageView imageView2;
        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.jiage)
        TextView jiage;
        @BindView(R.id.textView2)
        TextView textView2;
        @BindView(R.id.iv_add)
        ImageView addToCar;

        public MyViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
