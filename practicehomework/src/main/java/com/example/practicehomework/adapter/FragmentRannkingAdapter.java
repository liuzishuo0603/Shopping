package com.example.practicehomework.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.practicehomework.R;
import com.example.practicehomework.bean.ResultsBean;

import java.util.ArrayList;
import java.util.List;

public class FragmentRannkingAdapter extends RecyclerView.Adapter {
    public List<ResultsBean> mList = new ArrayList<>();
    private Context mContext;
    private onLongClickListener mLongClickListener;

    public FragmentRannkingAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_item_two, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        ResultsBean bean = mList.get(i);
        Glide.with(mContext).load(bean.getUrl()).placeholder(R.mipmap.a).apply(RequestOptions.circleCropTransform()).into(holder.mIv);
        holder.mTv.setText(bean.getDesc());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mLongClickListener != null) {
                    mLongClickListener.onLongClick(v, i);
                }
                return false;
            }
        });
    }

    public void setData(List<ResultsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public interface onLongClickListener {
        void onLongClick(View v, int position);
    }

    public void setOnLongClickListener(onLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIv;
        private TextView mTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv_ranking);
            mTv = itemView.findViewById(R.id.tv_ranking);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
