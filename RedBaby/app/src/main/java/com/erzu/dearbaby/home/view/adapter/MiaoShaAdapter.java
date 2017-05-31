package com.erzu.dearbaby.home.view.adapter;

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
import com.erzu.dearbaby.home.model.bean.MiaoShaBean;
import com.erzu.dearbaby.home.model.bean.Urls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MiaoShaAdapter extends RecyclerView.Adapter<MiaoShaAdapter.MiaoShaHolder> {


    public MiaoShaAdapter(Context context, List<MiaoShaBean> miaoShaBeanList) {
        this.context = context;
        this.miaoShaBeanList = miaoShaBeanList;
    }

    private Context context;
    private List<MiaoShaBean> miaoShaBeanList;

    @Override
    public MiaoShaHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_item2_item, parent, false);
        MiaoShaHolder holder = new MiaoShaHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(MiaoShaHolder holder, int position) {

        String picUrl = miaoShaBeanList.get(position).getPicUrl();
        String morenpicUrl = "http://image3.suning.cn/uimg/cms/img/149069422671422916.png";

        if (TextUtils.isEmpty(picUrl)) {
            Glide.with(context).load(morenpicUrl).into(holder.ivGoodIcon);
        } else {
            Glide.with(context).load(Urls.PIC_TITLE + picUrl).into(holder.ivGoodIcon);
        }

        holder.tvGoodPrice.setText(miaoShaBeanList.get(position).getPrice() + "$");
        holder.tvGoodOldPrice.setText(miaoShaBeanList.get(position).getPrice() + "￥");

    }

    @Override
    public int getItemCount() {

        return miaoShaBeanList != null ? miaoShaBeanList.size() : 0;
    }

    class MiaoShaHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_good_icon)
        ImageView ivGoodIcon;
        @BindView(R.id.tv_goodPrice)
        TextView tvGoodPrice;
        @BindView(R.id.tv_goodOldPrice)
        TextView tvGoodOldPrice;

        public MiaoShaHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
