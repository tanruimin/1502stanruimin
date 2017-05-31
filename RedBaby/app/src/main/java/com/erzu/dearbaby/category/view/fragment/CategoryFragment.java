package com.erzu.dearbaby.category.view.fragment;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.erzu.dearbaby.R;
import com.erzu.dearbaby.category.model.bean.TypeBean;
import com.erzu.dearbaby.category.view.activity.CoordinatorLayoutActivity;
import com.erzu.dearbaby.me.model.utils.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CategoryFragment extends Fragment {

    private int index;
    private View view;
    private RecyclerView mLeft;
    private RecyclerView mRight;
    private EditText mEditText;
    private ArrayList<View> mViews;
    private MyadapterLeft mMyadapterLeft;
    private ArrayList<TextView> mTextViews;
    private ArrayList<View> mLines;
    private MyadapterRight mMyadapterRight;
    //private ClassfiPresenterOb mClassfiPresenterOb;
    private String [] leftTitle={"宝宝奶粉","辅助营养","宝宝尿裤","洗护产品","喂养用品","宝宝玩乐","妈妈专区","童装童鞋","图书天地","童车童床"};
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    notifyAdapter();
                    initView();
                    break;
            }
        }
    };

    private void notifyAdapter() {
        mRsBean = mRs.get(index);
//            外层数据源mChildren
        mChildren = mRsBean.getChildren();

    }

    private List<TypeBean.RsBean> mRs;
    private MyadapterRightIn mMyadapterRightIn;
    private TypeBean.RsBean mRsBean;
    private List<TypeBean.RsBean.ChildrenBeanX> mChildren;
    private TypeBean.RsBean.ChildrenBeanX mBeanX;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.classifylayout, null);
      //  mClassfiPresenterOb = new ClassfiPresenterOb(this);
        //mClassfiPresenterOb.getLeftData();
        initData();


        return view;
    }

    public void initData() {
        OkHttpUtils.get("http://ds.suning.cn/ds/terminal/categoryInfo/99999999-.json", new Callback() {
            private Gson gson;

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mType = response.body().string();
                gson = new Gson();
                TypeBean typebean = gson.fromJson(mType, TypeBean.class);
                mRs = typebean.getRs();
                handler.sendEmptyMessage(0);



            }

        });
    }

    private void initView() {
        mViews = new ArrayList<>();
        mTextViews = new ArrayList<>();
        mLines = new ArrayList<>();

        mLeft = (RecyclerView) view.findViewById(R.id.rlv_classify_left);
        mLeft.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMyadapterLeft = new MyadapterLeft();

        mLeft.setAdapter(mMyadapterLeft);
        mLeft.addItemDecoration(new com.erzu.dearbaby.category.view.views.DividerItemDecoration(getActivity()));
        mRight = (RecyclerView) view.findViewById(R.id.rlv_classify_right);
        mRight.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEditText = (EditText) view.findViewById(R.id.editText);
        mRight.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMyadapterRight = new MyadapterRight();
        mRight.setAdapter(mMyadapterRight);


    }

    class ViewHolderLeft extends RecyclerView.ViewHolder {

        TextView mTv;
        private final View line;
        View view;

        public ViewHolderLeft(final View itemView) {
            super(itemView);
            mViews.add(itemView);
            view=itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    index= (int) view.getTag();
                    notifyAdapter();
                    mMyadapterRight
                            .notifyDataSetChanged();

                    for (View view1 : mViews) {

                        view1.setBackground(null);

                    }
                    for (TextView textView : mTextViews) {
                        textView.setTextColor(Color.parseColor("#2B2B2B"));
                    }
                    for (View line : mLines) {

                        line.setBackground(null);
                    }
                    line.setBackgroundColor(Color.parseColor("#F29400"));
                    view.setBackgroundColor(Color.parseColor("#EEEEEE"));
                    mTv.setTextColor(Color.parseColor("#F29400"));


                }
            });
            mTv = (TextView) itemView.findViewById(R.id.classfi_left_tv);
            line = itemView.findViewById(R.id.classfi_left_line);
            mLines.add(line);
            mTextViews.add(mTv);

        }
    }

    class MyadapterLeft extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(
//                    getActivity()).inflate(R.layout.classfileftitem, parent,
//                    false);
//
//            ViewHolderLeft holder = new ViewHolderLeft(view);
//            return holder;
            return  new ViewHolderLeft(LayoutInflater.from(
                    getActivity()).inflate(R.layout.classfileftitem, parent,
                    false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolderLeft viewholder= (ViewHolderLeft) holder;
            viewholder.mTv.setText(leftTitle[position]);
            viewholder.view.setTag(position);
            if (position==0){
                viewholder.mTv.setTextColor(Color.parseColor("#F29400"));
                viewholder.line.setBackgroundColor(Color.parseColor("#F29400"));
                mViews.get(0).setBackgroundColor(Color.parseColor("#EEEEEE"));
            }

        }

        @Override
        public int getItemCount() {
            return leftTitle.length;
        }

    }
    class MyadapterRight extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(
                    getActivity()).inflate(R.layout.classfirightitem, parent,
                    false);
            ViewHolderRight holder = new ViewHolderRight(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolderRight holderRight= (ViewHolderRight) holder;

            mBeanX = mChildren.get(position);
            holderRight.mType_text.setText(mBeanX.getDirName());
            holderRight.mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

            mMyadapterRightIn = new MyadapterRightIn();

            holderRight.mRecyclerView.setAdapter(mMyadapterRightIn);


        }

        @Override
        public int getItemCount() {
            return mChildren.size();
        }
    }

    class ViewHolderRight extends RecyclerView.ViewHolder{

        private final RecyclerView mRecyclerView;
        private final TextView mType_text;

        public ViewHolderRight(View itemView) {
            super(itemView);

            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyc_right_ex);
            mType_text = (TextView) itemView.findViewById(R.id.type_right);

        }

    }
    class MyadapterRightIn extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolderRightIn holder = new ViewHolderRightIn(LayoutInflater.from(
                    getActivity()).inflate(R.layout.classfirightitemin, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


            String dirName =   mBeanX.getChildren().get(position).getDirName();
            String imgApp = mBeanX.getChildren().get(position).getImgApp();
            ViewHolderRightIn holderRight= (ViewHolderRightIn) holder;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), CoordinatorLayoutActivity.class));
                }
            });

//            holderRight.mImageView
            holderRight.mTextView.setText(dirName);
            Glide.with(getActivity()).load(imgApp).into(  holderRight.mImageView);

        }

        @Override
        public int getItemCount() {
            return  mBeanX.getChildren().size();
        }
    }
    class ViewHolderRightIn extends RecyclerView.ViewHolder{


        private final ImageView mImageView;
        private final TextView mTextView;

        public ViewHolderRightIn(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.type_image);
            mTextView = (TextView) itemView.findViewById(R.id.type_name);

        }

    }}