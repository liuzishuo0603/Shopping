package com.example.shopping.bean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiSerivce {
    String mUrl = "http://www.qubaobei.com/";

    @GET("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
    Observable<RootBean> getData();
}
