package com.erzu.dearbaby.home.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.home.model.bean.MiaoShaBean;
import com.erzu.dearbaby.home.model.bean.OtherDataBean;
import com.erzu.dearbaby.home.model.bean.Urls;
import com.erzu.dearbaby.home.model.utils.MyRecyclerViewListener;
import com.erzu.dearbaby.home.view.activity.HomeWebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<OtherDataBean.DataBean> dataBeen;
    private boolean isFirst;
    private final int VIEW_TYPE0 = 0;
    private final int VIEW_TYPE1 = 1;
    private final int VIEW_TYPE2 = 2;
    private final int VIEW_TYPE3 = 3;
    private final int VIEW_TYPE4 = 4;

    public HomeRecAdapter(Context context, List<OtherDataBean.DataBean> dataBeen, boolean isFirst) {
        this.context = context;
        this.dataBeen = dataBeen;
        this.isFirst = isFirst;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case VIEW_TYPE0:
                View view0 = inflater.inflate(R.layout.home_item1, parent, false);
                holder = new HomeViewHolder0(view0);
                break;
            case VIEW_TYPE1:
                View view1 = inflater.inflate(R.layout.home_item2, parent, false);
                holder = new HomeViewHolder1(view1);
                break;
            case VIEW_TYPE2:
                View view2 = inflater.inflate(R.layout.home_item3, parent, false);
                holder = new HomeViewHolder2(view2);
                break;
            case VIEW_TYPE3:
                View view3 = inflater.inflate(R.layout.home_item4, parent, false);
                holder = new HomeViewHolder3(view3);
                break;
            case VIEW_TYPE4:
                View view4 = inflater.inflate(R.layout.home_item5, parent, false);
                holder = new HomeViewHolder4(view4);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case VIEW_TYPE0:
                HomeViewHolder0 holder0 = (HomeViewHolder0) holder;

                if (isFirst) {
                    holder0.titles.setVisibility(View.VISIBLE);
                }

                String picUrl = Urls.PIC_TITLE + dataBeen.get(0).getTag().get(0).getPicUrl();
                final String webUrl = dataBeen.get(0).getTag().get(0).getLinkUrl();

                Glide.with(context).load(picUrl).into(holder0.imgCoupon);
                holder0.imgCoupon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, HomeWebActivity.class);
                        intent.putExtra("web", webUrl);
                        context.startActivity(intent);
                    }
                });
                break;
            case VIEW_TYPE1:
                HomeViewHolder1 holder1 = (HomeViewHolder1) holder;

                String picUrl1 = Urls.PIC_TITLE + dataBeen.get(1).getTag().get(0).getPicUrl();
                Glide.with(context).load(picUrl1).into(holder1.sell);

                holder1.llHandrodMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String webUrl = dataBeen.get(1).getTag().get(0).getLinkUrl();
                        if (TextUtils.isEmpty(webUrl)) {
                            ToastUtils.showShortToast("没有更多");
                        } else {
                            Intent intent = new Intent(context, HomeWebActivity.class);
                            intent.putExtra("web", webUrl);
                            context.startActivity(intent);
                        }

                    }
                });
                holder1.titleList.setLayoutManager(new LinearLayoutManager(context,
                        LinearLayoutManager.HORIZONTAL, false));
                MyRecyclerViewListener listener1 = new MyRecyclerViewListener(context,
                        holder1.titleList);
                final List<MiaoShaBean> miaoShaBeanList = new ArrayList<>();
                List<OtherDataBean.DataBean.TagBean> tag = dataBeen.get(1).getTag();
                for (int i = 0; i < tag.size(); i++) {
                    if (i > 0) {
                        MiaoShaBean bean = new MiaoShaBean();
                        bean.setPicUrl(tag.get(i).getPicUrl());
                        bean.setLinkUrl(tag.get(i).getLinkUrl());
                        bean.setPrice(12 + i);
                        miaoShaBeanList.add(bean);
                    }
                }
                listener1.setOnItemClickListener(new MyRecyclerViewListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, View view) {
                        String linkUrl = miaoShaBeanList.get(position).getLinkUrl();
                        Intent intent = new Intent(context, HomeWebActivity.class);
                        intent.putExtra("web", linkUrl);
                        context.startActivity(intent);
                    }
                });
                holder1.titleList.setAdapter(new MiaoShaAdapter(context, miaoShaBeanList));
                break;
            case VIEW_TYPE2:
                HomeViewHolder2 holder2 = (HomeViewHolder2) holder;
                if (TextUtils.isEmpty(dataBeen.get(3).getTag().get(0).getPicUrl())) {
                    String picUrl2 = Urls.PIC_TITLE + dataBeen.get(3).getTag().get(0).getPicUrl();
                    Glide.with(context).load(picUrl2).into(holder2.active);
                }

                holder2.recViHd.setLayoutManager(new LinearLayoutManager(context));

                List<OtherDataBean.DataBean.TagBean> tag12 = new ArrayList<>();

                for (int i = 0; i < dataBeen.size(); i++) {
                    if (dataBeen.get(i).getModelFullId() == 10899) {
                        tag12.add(dataBeen.get(i).getTag().get(0));
                    }
                }
                holder2.recViHd.setAdapter(new HuoDongAdapter(context, tag12));
                break;
            case VIEW_TYPE3:
                HomeViewHolder3 holder3 = (HomeViewHolder3) holder;
                String picUr22 = Urls.PIC_TITLE + dataBeen.get(9).getTag().get(0).getPicUrl();
                Glide.with(context).load(picUr22).into(holder3.iv_lookMore);

                holder3.iv_lookMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String linkUrl = dataBeen.get(9).getTag().get(0).getLinkUrl();
                        Intent intent = new Intent(context, HomeWebActivity.class);
                        intent.putExtra("web", linkUrl);
                        context.startActivity(intent);
                    }
                });
                break;
            case VIEW_TYPE4:
                HomeViewHolder4 holder4 = (HomeViewHolder4) holder;
                String picUr32 = Urls.PIC_TITLE + dataBeen.get(11).getTag().get(0).getPicUrl();
                Glide.with(context).load(picUr32).into(holder4.iv_tm);
                holder4.recViTm.setLayoutManager(new LinearLayoutManager(context));
                List<OtherDataBean.DataBean> mydataBeen = new ArrayList<>();
                for (int i = 0; i < dataBeen.size(); i++) {
                    if (dataBeen.get(i).getModelFullId() == 10108 ||
                            dataBeen.get(i).getModelFullId() == 10109) {
                        mydataBeen.add(dataBeen.get(i));
                    }
                }
                holder4.recViTm.setAdapter(new TeMaiAdapter(context, mydataBeen));
                break;
        }

    }

    @Override
    public int getItemCount() {
//        return list != null ? list.size() : 0;

        return 5;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return VIEW_TYPE0;
        } else if (position == 1) {
            return VIEW_TYPE1;
        } else if (position == 2) {
            return VIEW_TYPE2;
        } else if (position == 3) {
            return VIEW_TYPE3;
        } else if (position == 4) {
            return VIEW_TYPE4;
        }

        return super.getItemViewType(position);
    }

    class HomeViewHolder0 extends RecyclerView.ViewHolder {

        @BindView(R.id.img_coupon)
        ImageView imgCoupon;
        @BindView(R.id.titles)
        LinearLayout titles;

        public HomeViewHolder0(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HomeViewHolder1 extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_handrod_more)
        LinearLayout llHandrodMore;
        @BindView(R.id.title_list)
        RecyclerView titleList;
        @BindView(R.id.iv_sell)
        ImageView sell;

        public HomeViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HomeViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.recVi_hd)
        RecyclerView recViHd;
        @BindView(R.id.iv_active)
        ImageView active;

        public HomeViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HomeViewHolder3 extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_lookmore)
        ImageView iv_lookMore;

        public HomeViewHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HomeViewHolder4 extends RecyclerView.ViewHolder {

        @BindView(R.id.recVi_tm)
        RecyclerView recViTm;
        @BindView(R.id.iv_tm)
        ImageView iv_tm;

        public HomeViewHolder4(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
