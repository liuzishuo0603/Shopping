package com.example.practicehomework.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.practicehomework.R;
import com.example.practicehomework.bean.ResultsBean;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FragmentHomeAdapter extends RecyclerView.Adapter {
    private static final int ONE = 1;
    private static final int TWO = 2;
    public List<ResultsBean> mList = new ArrayList<>();
    private Context mContext;
    private onClickListener mListener;
    private onLongClickListener mLongClickListener;
    private int positions;

    public FragmentHomeAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (i == ONE) {
            View view = inflater.inflate(R.layout.fragment_item_list_one, null);
            return new MyViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.fragment_item_list_two, null);
            return new MyViewHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        if (type == ONE && viewHolder instanceof MyViewHolder) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            holder.mBanners.setImages(mList).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    ResultsBean bean = (ResultsBean) path;
                    Glide.with(mContext).load(bean.getUrl()).placeholder(R.mipmap.a).into(imageView);
                }
            }).start().setBannerAnimation(Transformer.CubeOut).setDelayTime(2500);
        } else if (type == TWO && viewHolder instanceof MyViewHolder2) {
            if (mList.size() > 0) {
                positions = i +1;
            }
            MyViewHolder2 holder2 = (MyViewHolder2) viewHolder;
            ResultsBean bean = mList.get(positions);
            holder2.mTv.setText(bean.getWho());
            if (positions % 2 == 0) {
                Glide.with(mContext).load(bean.getUrl()).placeholder(R.mipmap.b).apply(RequestOptions.circleCropTransform()).into(holder2.mIv);
            } else {
                Glide.with(mContext).load(bean.getUrl()).placeholder(R.mipmap.b).apply(RequestOptions.bitmapTransform(new RoundedCorners(15))).into(holder2.mIv);
            }
            final int finalI = positions;
            holder2.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClick(v, finalI);
                    }
                }
            });
            final int finalI1 = positions;
            holder2.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mLongClickListener != null) {
                        mLongClickListener.onLongClick(v, finalI1);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mList.size() > 0) {
            return ONE;
        } else {
            return TWO;
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() > 0) {
            return mList.size() + 1;
        } else {
            return mList.size();
        }
    }

    public void setData(List<ResultsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private Banner mBanners;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBanners = itemView.findViewById(R.id.banner_fragment_home);
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {

        private TextView mTv;
        private ImageView mIv;

        public MyViewHolder2(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv_home);
            mTv = itemView.findViewById(R.id.tv_home);
        }
    }

    public interface onClickListener {
        void onClick(View v, int position);
    }

    public interface onLongClickListener {
        void onLongClick(View v, int position);

    }

    public void setOnClickListener(onClickListener listener) {
        mListener = listener;
    }

    public void setOnLongClickListener(onLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }
}
