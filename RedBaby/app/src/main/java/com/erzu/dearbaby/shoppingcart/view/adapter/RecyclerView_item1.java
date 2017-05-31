package com.erzu.dearbaby.shoppingcart.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.shoppingcart.model.bean.GoodsForCart;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerView_item1 extends RecyclerView.Adapter<RecyclerView_item1.MyViewHolder> {


    private List<GoodsForCart> carts;
    private int count;

    public RecyclerView_item1(Context contexts, List<GoodsForCart> carts) {
        this.contexts = contexts;
        this.carts = carts;
    }

    private Context contexts;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexts).inflate(R.layout.activity_shopping_item, parent, false);
        MyViewHolder viewholder = new MyViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.shoppingdianpuBnt.setChecked(true);
        holder.shoppingName.setText("商铺名");

        if (carts != null && carts.size() != 0) {
            if (carts.get(position).getIsChecked() == 1) {
                holder.shoppingnameBnt.setChecked(true);
            }

            Glide.with(contexts).load(carts.get(position).getPicUrl()).into(holder.shoppingIv);
            holder.shoppingInpo.setText(carts.get(position).getTitle());
            holder.shoppingPrice.setText(carts.get(position).getPrice() + "");
            count = carts.get(position).getCount();
            holder.tvNum.setText(count + "");
            if (count == 1) {
                holder.tvReduce.setTextColor(contexts.getResources().getColor(R.color.grgray));
            } else {
                holder.tvReduce.setTextColor(contexts.getResources().getColor(R.color.grey_color1));
            }
        }
        holder.tvReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (++count < 0) {
                    count--;
                    ToastUtils.showShortToast("商品数需大于一");

                } else {
                    holder.tvNum.setText(count + "");
                }
            }
        });
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (--count < 0) {
                    count++;
                    ToastUtils.showShortToast("商品数需大于一");

                } else {
                    holder.tvNum.setText(count + "");
                }
            }
        });

        holder.tvNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String numString = s.toString();
                if (numString == null || numString.equals("")) {
                    count = 0;
                } else {
                    int numInt = Integer.parseInt(numString);
                    if (numInt < 0) {
                        ToastUtils.showShortToast("商品数需大于一");
                    } else {
                        //设ditText光标位置 为文本末端
                        holder.tvNum.setSelection(holder.tvNum.getText().toString().length());
                        count = numInt;
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return carts != null ? carts.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shoppingdianpu_bnt)
        CheckBox shoppingdianpuBnt;
        @BindView(R.id.shopping_name)
        TextView shoppingName;
        @BindView(R.id.shoppingname_bnt)
        CheckBox shoppingnameBnt;
        @BindView(R.id.shopping_iv)
        ImageView shoppingIv;
        @BindView(R.id.shopping_inpo)
        TextView shoppingInpo;
        @BindView(R.id.shopping_price)
        TextView shoppingPrice;
        @BindView(R.id.tv_reduce)
        TextView tvReduce;
        @BindView(R.id.tv_num)
        EditText tvNum;
        @BindView(R.id.tv_add)
        TextView tvAdd;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
