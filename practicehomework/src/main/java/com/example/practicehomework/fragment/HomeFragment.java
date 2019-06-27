package com.example.practicehomework.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.practicehomework.R;
import com.example.practicehomework.adapter.FragmentHomeAdapter;
import com.example.practicehomework.bean.ApiSerivce;
import com.example.practicehomework.bean.MyApp;
import com.example.practicehomework.bean.ResultsBean;
import com.example.practicehomework.bean.RooBean;
import com.example.xts.greendaodemo.db.DaoSession;
import com.example.xts.greendaodemo.db.ResultsBeanDao;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends BaseFragment {
    private View view;
    private RecyclerView mRlv;
    private SmartRefreshLayout mSrlFragmentHome;
    private FragmentHomeAdapter mAdapter;
    private static final String TAG = "HomeFragment";
    private int mPage = 1;
    private int positions;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    public void initView(View view) {
        mRlv = (RecyclerView) view.findViewById(R.id.rlv_fragment_home);
        mSrlFragmentHome = (SmartRefreshLayout) view.findViewById(R.id.srl_fragment_home);
        mRlv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRlv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new FragmentHomeAdapter(getActivity());
        mRlv.setAdapter(mAdapter);
        mSrlFragmentHome.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                mAdapter.mList.clear();
                initData();
                mSrlFragmentHome.finishRefresh();
            }
        });
        mSrlFragmentHome.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                initData();
                mSrlFragmentHome.finishLoadMore();
            }
        });
        mAdapter.setOnClickListener(new FragmentHomeAdapter.onClickListener() {
            @Override
            public void onClick(View v, int position) {
                DaoSession daoSession = MyApp.getmDaoSession();
                ResultsBean bean = mAdapter.mList.get(position);
                ResultsBean unique = daoSession.queryBuilder(ResultsBean.class).where(ResultsBeanDao.Properties._id.eq(bean.get_id())).build().unique();
                if (unique == null) {
                    daoSession.insert(bean);
                    Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "此文件已存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAdapter.setOnLongClickListener(new FragmentHomeAdapter.onLongClickListener() {
            @Override
            public void onLongClick(View v, int position) {
                positions = position;
            }
        });
        registerForContextMenu(mRlv);//注册上下文
    }

    @Override
    protected void initData() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiSerivce.mUrl)
                .build();
        retrofit.create(ApiSerivce.class).getData(mPage).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RooBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(RooBean rooBean) {
                        if (rooBean != null) {
                            List<ResultsBean> list = rooBean.getResults();
                            Log.d(TAG, "onNext: 数据：" + list.size());
                            if (list.size() > 0) {
                                mAdapter.setData(list);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: 错误数据：" + e.getCause().getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 11, 1, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 11:
                ResultsBean bean = mAdapter.mList.get(positions);
                mAdapter.mList.remove(bean);
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
