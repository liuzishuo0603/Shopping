package com.example.shopping.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopping.MainActivity;
import com.example.shopping.R;
import com.example.shopping.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter {
    public List<DataBean> mList = new ArrayList<>();
    private Context mContext;
    private onClickListener mListener;

    public MainAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_item_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MyViewHolder holder = (MyViewHolder) viewHolder;
        final DataBean bean = mList.get(i);
        Glide.with(mContext).load(bean.getPic()).apply(RequestOptions.circleCropTransform()).placeholder(R.mipmap.ic_launcher).into(holder.mIv);
        holder.mTv.setText(bean.getTitle());
        holder.mTv2.setText("￥: " + bean.getNum());
        final Integer integer = new Integer(i);
        holder.mCb.setTag(integer);
        if (MainActivity.map.containsKey(integer)) {
            holder.mCb.setChecked(true);
        } else {
            holder.mCb.setChecked(false);
        }
        holder.mCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mCb.isChecked()) {
                    MainActivity.map.put(integer, true);
                } else {
                    MainActivity.map.remove(integer);
                }
                if (mListener != null) {
                    mListener.onClick(bean.isIscheck(), i,v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface onClickListener {
        void onClick(boolean isCheck, int position,View v);
    }

    public void setOnClickListener(onClickListener listener) {
        mListener = listener;
    }

    public void setData(List<DataBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCb;
        private ImageView mIv;
        private TextView mTv, mTv2;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCb = itemView.findViewById(R.id.cb_main);
            mIv = itemView.findViewById(R.id.iv_main);
            mTv = itemView.findViewById(R.id.tv_main);
            mTv2 = itemView.findViewById(R.id.tv_main1);
        }
    }
}
