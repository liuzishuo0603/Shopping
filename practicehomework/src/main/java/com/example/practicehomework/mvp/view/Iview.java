package com.example.practicehomework.mvp.view;

import com.example.practicehomework.bean.RooBean;

public interface Iview {
    void onSuccess(RooBean rooBean);

    void onFail(String error);
}
