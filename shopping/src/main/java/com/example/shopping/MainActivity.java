package com.example.shopping;
//刘子硕  2019/6/27/16:55

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.shopping.adapter.MainAdapter;
import com.example.shopping.bean.ApiSerivce;
import com.example.shopping.bean.DataBean;
import com.example.shopping.bean.RootBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRlv;
    private MainAdapter mAdapter;
    private TextView mTv;
    private static final String TAG = "MainActivity";
    private int currentCount;

    public static HashMap<Integer, Boolean> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiSerivce.mUrl)
                .build();
        retrofit.create(ApiSerivce.class).getData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RootBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(RootBean rootBean) {
                        if (rootBean != null) {
                            List<DataBean> list = rootBean.getData();
                            mAdapter.setData(list);
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

    private void initView() {
        mRlv = (RecyclerView) findViewById(R.id.rlv_main);
        mTv = (TextView) findViewById(R.id.tv);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mRlv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new MainAdapter(this);
        mRlv.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new MainAdapter.onClickListener() {
            @Override
            public void onClick(boolean isCheck, int position, View v) {
                DataBean bean = mAdapter.mList.get(position);
                CheckBox cb = v.findViewById(R.id.cb_main);
                int num = bean.getNum();
                if (cb.isChecked()) {
                    currentCount += num;
                    mTv.setText("￥： " + currentCount);
                } else {
                    currentCount -= num;
                    mTv.setText("￥：" + currentCount);
                }
            }
        });
    }
}
