package com.example.practicehomework.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.practicehomework.R;
import com.example.practicehomework.adapter.FragmentHomeAdapter;
import com.example.practicehomework.adapter.FragmentRannkingAdapter;
import com.example.practicehomework.bean.MyApp;
import com.example.practicehomework.bean.ResultsBean;
import com.example.xts.greendaodemo.db.DaoSession;

import java.util.List;

public class RankingFragment extends BaseFragment {
    private View view;
    private RecyclerView mRlv;
    private FragmentRannkingAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private int positions;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    public void initView(View view) {
        setHasOptionsMenu(true);
        mRlv = (RecyclerView) view.findViewById(R.id.rlv_ranking);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRlv.setLayoutManager(mLinearLayoutManager);
        mRlv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new FragmentRannkingAdapter(getActivity());
        mRlv.setAdapter(mAdapter);
        mAdapter.setOnLongClickListener(new FragmentRannkingAdapter.onLongClickListener() {
            @Override
            public void onLongClick(View v, int position) {
                positions = position;
            }
        });
        registerForContextMenu(mRlv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                ResultsBean bean = mAdapter.mList.get(positions);
                mAdapter.mList.remove(bean);
                DaoSession daoSession = MyApp.getmDaoSession();
                daoSession.delete(bean);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void initData() {
        DaoSession daoSession = MyApp.getmDaoSession();
        List<ResultsBean> list = daoSession.loadAll(ResultsBean.class);
        mAdapter.setData(list);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(1, 1, 1, "线性布局管理器");
        menu.add(1, 2, 1, "网格布局管理器");
        menu.add(1, 3, 1, "瀑布流管理器");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                if (mLinearLayoutManager == null) {
                    mLinearLayoutManager = new LinearLayoutManager(getActivity());
                }
                mRlv.setLayoutManager(mLinearLayoutManager);
                break;
            case 2:
                if (mGridLayoutManager == null) {
                    mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
                }
                mRlv.setLayoutManager(mGridLayoutManager);
                break;
            case 3:
                if (mStaggeredGridLayoutManager == null) {
                    mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                }
                mRlv.setLayoutManager(mStaggeredGridLayoutManager);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
