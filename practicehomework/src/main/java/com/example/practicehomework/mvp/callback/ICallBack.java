package com.example.practicehomework.mvp.callback;

import com.example.practicehomework.bean.RooBean;

public interface ICallBack {
    void onSuccess(RooBean rooBean);

    void onFail(String error);
}
