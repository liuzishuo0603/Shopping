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

import retrofit2.http.POST;

public class FragmentVideoAdapter extends RecyclerView.Adapter {
    public List<ResultsBean> mList = new ArrayList<>();
    private Context mContext;
    private onClickListener mListener;
    private onLongClickListener mLongClickListener;

    public FragmentVideoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ResultsBean bean = mList.get(i);
        MyViewHolder holder = (MyViewHolder) viewHolder;
        Glide.with(mContext).load(bean.getUrl()).apply(RequestOptions.circleCropTransform()).placeholder(R.mipmap.c).into(holder.mIv);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(v, i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<ResultsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIv;
        private TextView mTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv_video);
            mTv = itemView.findViewById(R.id.tv_video);
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
