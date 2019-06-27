package com.example.practicehomework.mvp.model;

import android.util.Log;

import com.example.practicehomework.bean.ApiSerivce;
import com.example.practicehomework.bean.ResultsBean;
import com.example.practicehomework.bean.RooBean;
import com.example.practicehomework.mvp.callback.ICallBack;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model implements Imodel {
    private static final String TAG = "Model";

    @Override
    public void getData(int page, final ICallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiSerivce.mUrl)
                .build();
        retrofit.create(ApiSerivce.class).getData(page).subscribeOn(Schedulers.newThread())
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
                                callBack.onSuccess(rooBean);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: 错误数据：" + e.getCause().getMessage());
                        callBack.onFail(e.getCause().getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
