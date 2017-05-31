package com.erzu.dearbaby.home.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.home.model.bean.OtherDataBean;
import com.erzu.dearbaby.home.model.bean.Urls;
import com.erzu.dearbaby.home.model.utils.MyRecyclerViewListener;
import com.erzu.dearbaby.home.view.activity.HomeWebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TeMaiAdapter extends RecyclerView.Adapter<TeMaiAdapter.TeMaiHolder> {


    private Context context;
    private List<OtherDataBean.DataBean> dataBeen;

    public TeMaiAdapter(Context context, List<OtherDataBean.DataBean> dataBeen) {

        this.context = context;
        this.dataBeen = dataBeen;
    }


    @Override
    public TeMaiHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.home_item5_item, parent, false);

//        View view = View.inflate(context, R.layout.home_item5_item, null);

        TeMaiHolder holder = new TeMaiHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final TeMaiHolder holder, final int position) {

        if (dataBeen.get(position).getModelFullId() == 10108) {

            String picUrl = Urls.PIC_TITLE + dataBeen.get(position).getTag().get(0).getPicUrl();
            Glide.with(context).load(picUrl).into(holder.imgCoupon);
            holder.recViMx.setVisibility(View.GONE);

        } else if (dataBeen.get(position).getModelFullId() == 10109) {
            holder.recViMx.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            MyRecyclerViewListener listener = new MyRecyclerViewListener(context,
                    holder.recViMx);
            listener.setOnItemClickListener(new MyRecyclerViewListener.OnItemClickListener() {
                @Override
                public void onItemClick(int position1, View view) {
                    String linkUrl = dataBeen.get(position).getTag().get(position1).getLinkUrl();
                    Intent intent = new Intent(context, HomeWebActivity.class);
                    intent.putExtra("web", linkUrl);
                    context.startActivity(intent);
                }
            });

            holder.imgCoupon.setVisibility(View.GONE);

            List<OtherDataBean.DataBean.TagBean> tagBeanList = dataBeen.get(position).getTag();
            holder.recViMx.setAdapter(new MXAdapter(context, tagBeanList));
        }


    }

    @Override
    public int getItemCount() {


        return dataBeen != null ? dataBeen.size() : 0;
    }

    class TeMaiHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_coupon)
        ImageView imgCoupon;
        @BindView(R.id.recVi_mx)
        RecyclerView recViMx;

        public TeMaiHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
