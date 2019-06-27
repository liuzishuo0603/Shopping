package com.example.dell.myapplication.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.dell.myapplication.ApiSerivce;
import com.example.dell.myapplication.R;
import com.example.dell.myapplication.adapter.HomeAdapter;
import com.example.dell.myapplication.bean.SportsBannBean;
import com.example.dell.myapplication.bean.SportsBean;

import org.json.JSONObject;

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
    private HomeAdapter mAdapter;
    private static final String TAG = "HomeFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        initDatas();
        initDate();
    }

    private void initDatas() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiSerivce.mUrl)
                .build();
        retrofit.create(ApiSerivce.class).getData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SportsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(SportsBean sportsBean) {
                        if (sportsBean != null) {
                            List<SportsBean.ArticlesBean> articles = sportsBean.getArticles();
                            List<SportsBean.RecommendBean> recommend = sportsBean.getRecommend();
                            Log.d(TAG, "onNext: 数据：" + articles.size() + ":" + recommend.size());
                            if (articles.size() > 0 && recommend.size() > 0) {
                                mAdapter.mList.addAll(recommend);
                                mAdapter.mArticlesBeans.addAll(articles);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getCause().getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void initDate() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSerivce.mBanner)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofit.create(ApiSerivce.class).getDate().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SportsBannBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(SportsBannBean sportsBannBean) {
                        Log.d(TAG, "onNext: 我的数据：" + sportsBannBean.getFs_B());
                        if (sportsBannBean != null) {
                            mAdapter.mSportsBannBeans.add(sportsBannBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getCause().getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    public void initView(View view) {
        mRlv = (RecyclerView) view.findViewById(R.id.rlv_main);
        mRlv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRlv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new HomeAdapter(getActivity());
        mRlv.setAdapter(mAdapter);
    }
}
