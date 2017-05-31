package com.erzu.dearbaby.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.home.model.bean.OtherDataBean;
import com.erzu.dearbaby.home.model.bean.Urls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HuoDongAdapter extends RecyclerView.Adapter<HuoDongAdapter.HuoDongHolder> {


    private Context context;
    private List<OtherDataBean.DataBean.TagBean> tag;

    public HuoDongAdapter(Context context, List<OtherDataBean.DataBean.TagBean> tag) {
        this.context = context;
        this.tag = tag;
    }

    @Override
    public HuoDongHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_item3_item, parent, false);

        HuoDongHolder holder = new HuoDongHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(HuoDongHolder holder, int position) {

        String picUr = Urls.PIC_TITLE + tag.get(position).getPicUrl();
        Glide.with(context).load(picUr).into(holder.imgProduct);
        holder.tvProductName.setText(tag.get(position).getElementName());
        holder.tvProductDesc.setText(tag.get(position).getElementDesc());
        holder.tvPrice.setText("$95");
        holder.tvRefPrice.setText("$25");
        holder.tvClustering.setText("3人团购");
        holder.tvToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("跳转到团购页");
            }
        });

    }

    @Override
    public int getItemCount() {
        return tag.size();
    }


    class HuoDongHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_product)
        ImageView imgProduct;
        @BindView(R.id.img_product_bg)
        ImageView imgProductBg;
        @BindView(R.id.img_sold_out)
        ImageView imgSoldOut;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_desc)
        TextView tvProductDesc;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_ref_price)
        TextView tvRefPrice;
        @BindView(R.id.tv_clustering)
        TextView tvClustering;
        @BindView(R.id.tv_to_buy)
        TextView tvToBuy;

        public HuoDongHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
