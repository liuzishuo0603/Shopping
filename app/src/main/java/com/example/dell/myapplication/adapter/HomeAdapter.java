package com.example.dell.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dell.myapplication.R;
import com.example.dell.myapplication.bean.SportsBannBean;
import com.example.dell.myapplication.bean.SportsBean;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter {
    private Context mContext;
    public List<SportsBean.ArticlesBean> mArticlesBeans = new ArrayList<>();
    public List<SportsBean.RecommendBean> mList = new ArrayList<>();
    public List<SportsBannBean> mSportsBannBeans = new ArrayList<>();
    private int positions;

    public HomeAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (i == 0) {
            View view = inflater.inflate(R.layout.banner_item, null);
            return new MyViewHolder1(view);
        } else if (i == 1) {
            View view = inflater.inflate(R.layout.banner_item_list, null);
            return new MyViewHolder1(view);
        } else if (i == 2) {
            View view = inflater.inflate(R.layout.banner_item_list_one, null);
            return new MyViewHolder1(view);
        } else {
            View view = inflater.inflate(R.layout.banner_item_list_two, null);
            return new MyViewHolder1(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        if (type == 0) {
            MyViewHolder1 holder1 = (MyViewHolder1) viewHolder;
            holder1.mBanners.setImages(mList).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    SportsBean.RecommendBean recommendBean = (SportsBean.RecommendBean) path;
                    Glide.with(mContext).load(recommendBean.getThumb()).placeholder(R.mipmap.ic_launcher).into(imageView);
                }
            }).start().setBannerAnimation(Transformer.ZoomOutSlide).setDelayTime(2000);
        } else if (type == 1) {
            if (mList.size() > 0) {
                positions = i - 1;
            }
            MyViewHolder2 holder2 = (MyViewHolder2) viewHolder;
            SportsBannBean bean = mSportsBannBeans.get(positions);
            Glide.with(mContext).load(bean.getTeam_A_logo()).placeholder(R.mipmap.ic_launcher).into(holder2.mIv);
            Glide.with(mContext).load(bean.getTeam_B_logo()).placeholder(R.mipmap.ic_launcher).into(holder2.mIv2);
            holder2.mTv1.setText(bean.getMatch_title());
            holder2.mTv2.setText(bean.getFs_A() + "-" + bean.getFs_B());
            holder2.mTv3.setText("已结束");
            holder2.mTv4.setText(bean.getTeam_A_name() + "(" + bean.getPlayoff_fs_A() + ")");
            holder2.mTv5.setText(bean.getTeam_B_name() + "(" + bean.getPlayoff_fs_B() + ")");
        } else if (type == 2) {
            if (mList.size() > 0 && mSportsBannBeans.size() > 0) {
                positions = i - 1;
            }
           /*MyViewHolder3 holder3 = (MyViewHolder3) viewHolder;
            SportsBean.ArticlesBean bean = mArticlesBeans.get(positions);
            Glide.with(mContext).load(bean.getThumb()).placeholder(R.mipmap.ic_launcher).apply(RequestOptions.circleCropTransform()).into(holder3.mIv);
            holder3.mTv.setText(bean.getTitle());*/
        } else {
            MyViewHolder4 holder4 = (MyViewHolder4) viewHolder;
            SportsBean.ArticlesBean bean = mArticlesBeans.get(i);
            SportsBean.ArticlesBean.VideoInfoBean video_info = bean.getVideo_info();
            holder4.mVd.setVideoPath(video_info.getVideo_src());
            holder4.mVd.requestFocus();
            holder4.mVd.start();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1 && mSportsBannBeans.size() > 0) {
            return 1;
        } else if (mArticlesBeans.size() > 0) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0) {
            return mArticlesBeans.size() + 1;
        } else if (mSportsBannBeans != null && mSportsBannBeans.size() > 0) {
            return mSportsBannBeans.size();
        } else {
            return mArticlesBeans.size();
        }
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {

        private Banner mBanners;

        public MyViewHolder1(View itemView) {
            super(itemView);
            mBanners = itemView.findViewById(R.id.banner_home);
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        private ImageView mIv;
        private ImageView mIv2;
        private TextView mTv1;
        private TextView mTv2;
        private TextView mTv3;
        private TextView mTv4;
        private TextView mTv5;

        public MyViewHolder2(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv_home_yi);
            mIv2 = itemView.findViewById(R.id.iv_home_yi2);
            mTv1 = itemView.findViewById(R.id.tv_home_yi);
            mTv2 = itemView.findViewById(R.id.tv_home_yi2);
            mTv3 = itemView.findViewById(R.id.tv_home_yi3);
            mTv4 = itemView.findViewById(R.id.tv_home_yi4);
            mTv5 = itemView.findViewById(R.id.tv_home_yi5);
        }
    }

    class MyViewHolder3 extends RecyclerView.ViewHolder {
        private TextView mTv;
        private ImageView mIv;

        public MyViewHolder3(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv_home_er);
            mTv = itemView.findViewById(R.id.tv_home_er);
        }
    }

    class MyViewHolder4 extends RecyclerView.ViewHolder {

        private VideoView mVd;

        public MyViewHolder4(View itemView) {
            super(itemView);
            mVd = itemView.findViewById(R.id.vd_home);
        }
    }
}
