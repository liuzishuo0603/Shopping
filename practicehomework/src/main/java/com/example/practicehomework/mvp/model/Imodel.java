package com.example.practicehomework.mvp.model;

import com.example.practicehomework.mvp.callback.ICallBack;

public interface Imodel {
    void getData(int page, ICallBack callBack);
}
