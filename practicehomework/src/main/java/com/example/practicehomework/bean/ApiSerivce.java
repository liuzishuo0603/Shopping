package com.example.practicehomework.bean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiSerivce {
    String mUrl = "http://gank.io/";

    @GET("api/data/%E7%A6%8F%E5%88%A9/20/{page}")
    Observable<RooBean> getData(@Path("page") int page);

    String uploadUrl = "http://yun918.cn/";
    @POST("study/public/file_upload.php")
    @Multipart
    Call<ResponseBody> postImage(@Part("key") RequestBody requestBody , @Part MultipartBody.Part mu);
}
