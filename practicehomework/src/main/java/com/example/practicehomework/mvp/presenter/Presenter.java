package com.example.practicehomework.mvp.presenter;

import com.example.practicehomework.bean.RooBean;
import com.example.practicehomework.mvp.callback.ICallBack;
import com.example.practicehomework.mvp.model.Model;
import com.example.practicehomework.mvp.view.Iview;

import javax.security.auth.callback.Callback;

public class Presenter implements Ipresenter {
    private Iview mIview;
    private final Model mMode;

    public Presenter(Iview iview) {
        mIview = iview;
        mMode = new Model();
    }

    @Override
    public void setData(int page) {
        if (mMode != null) {
            mMode.getData(page, new ICallBack() {
                @Override
                public void onSuccess(RooBean rooBean) {
                    if (rooBean != null) {
                        mIview.onSuccess(rooBean);
                    }
                }

                @Override
                public void onFail(String error) {
                    mIview.onFail(error);
                }
            });
        }
    }
}
