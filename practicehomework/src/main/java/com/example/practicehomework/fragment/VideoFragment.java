package com.example.practicehomework.fragment;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.practicehomework.R;
import com.example.practicehomework.WebActivity;
import com.example.practicehomework.adapter.FragmentHomeAdapter;
import com.example.practicehomework.adapter.FragmentVideoAdapter;
import com.example.practicehomework.bean.ResultsBean;
import com.example.practicehomework.bean.RooBean;
import com.example.practicehomework.mvp.presenter.Presenter;
import com.example.practicehomework.mvp.view.Iview;

import java.util.List;

public class VideoFragment extends BaseFragment implements Iview {
    private View view;
    private RecyclerView mRlv;
    private FragmentVideoAdapter mAdapter;
    private int positions;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    public void initView(View view) {
        mRlv = (RecyclerView) view.findViewById(R.id.rlv_fragment_video);
        mRlv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRlv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new FragmentVideoAdapter(getActivity());
        mRlv.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new FragmentVideoAdapter.onClickListener() {
            @Override
            public void onClick(View v, final int position) {
                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.popup_item, null);
                final PopupWindow popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                LinearLayout ll = inflate.findViewById(R.id.ll_popupwindow);
                Button but = inflate.findViewById(R.id.but_popupwindow);
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.mList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(mRlv, Gravity.CENTER, 0, 0);
            }
        });
        mAdapter.setOnLongClickListener(new FragmentVideoAdapter.onLongClickListener() {
            @Override
            public void onLongClick(View v, int position) {
                positions = position;
            }
        });
        registerForContextMenu(mRlv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "详情");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                ResultsBean bean = mAdapter.mList.get(positions);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", bean.getUrl());
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void initData() {
        Presenter presenter = new Presenter(this);
        presenter.setData(1);
    }

    @Override
    public void onSuccess(RooBean rooBean) {
        if (rooBean != null) {
            List<ResultsBean> list = rooBean.getResults();
            mAdapter.setData(list);
        }
    }

    @Override
    public void onFail(String error) {
        Toast.makeText(getActivity(), "错误" + error, Toast.LENGTH_SHORT).show();
    }
}
