package com.example.practicehomework.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.practicehomework.R;
import com.example.practicehomework.TestActivity;
import com.example.practicehomework.bean.ApiSerivce;
import com.example.practicehomework.bean.MyService;
import com.example.practicehomework.bean.UpLoad_Fragment_RootBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static android.provider.Telephony.Carriers.SERVER;

public class ChatFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "ChatFragment";
    private View view;
    public static String TEST = "com.example.lianxi1.test";
    /**
     * okhttp上传文件
     */
    private Button mBu1;
    /**
     * retrofit上传文件
     */
    private Button mBu2;
    /**
     * server下载
     */
    private Button mBu3;
    /**
     * 广播测试
     */
    private Button mBu4;
    private ImageView mF3Img;
    private ProgressBar mProgressBar;
    String uploadUrl = "http://yun918.cn/study/public/file_upload.php";
    public static String SERVER = "com.example.lianxi1.server";
    private MyBroadcast myBroadcast;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    public void initView(View view) {
        mBu1 = (Button) view.findViewById(R.id.bu1);
        mBu1.setOnClickListener(this);
        mBu2 = (Button) view.findViewById(R.id.bu2);
        mBu2.setOnClickListener(this);
        mBu3 = (Button) view.findViewById(R.id.bu3);
        mBu3.setOnClickListener(this);
        mBu4 = (Button) view.findViewById(R.id.bu4);
        mBu4.setOnClickListener(this);
        mF3Img = (ImageView) view.findViewById(R.id.f3_img);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        myBroadcast = new MyBroadcast();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SERVER);
        intentFilter.addAction(TEST);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(myBroadcast, intentFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bu1:
                initOkHttp();
                break;
            case R.id.bu2:
                initRetorfit();
                break;
            case R.id.bu3:
                startServer();
                break;
            case R.id.bu4:
                Intent intent = new Intent();
                intent.setAction(TEST);
                intent.putExtra("test", "我是广播事件");
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                break;
        }
    }

    private void startServer() {
        Intent intent = new Intent(getActivity(), MyService.class);
        getActivity().startService(intent);
    }

    class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == SERVER) {
                int data = intent.getIntExtra("data", 0);
                mProgressBar.setProgress(data);
            } else if (intent.getAction() == TEST) {
                String test = intent.getStringExtra("test");
                Intent intent1 = new Intent(getActivity(), TestActivity.class);
                intent1.putExtra("data", test);
                PendingIntent activity = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager systemService = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("通知")
                        .setContentText(test)
                        .setContentIntent(activity)
                        .setAutoCancel(true)
                        .build();
                systemService.notify(1, notification);
            }
        }
    }

    private void initRetorfit() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "1811");

        String fileUrl = Environment.getExternalStorageDirectory() + File.separator;
        File file = new File(fileUrl + "a.jpg");
        if (!file.exists()) {
            Log.d(TAG, "okhttpPost: 不存在");
            return;
        }
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part file1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);

        new Retrofit.Builder()
                .baseUrl(ApiSerivce.uploadUrl)
                .build()
                .create(ApiSerivce.class)
                .postImage(requestBody, file1)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        try {
                            String string = response.body().string();
                            JSONObject jsonObject = new JSONObject(string);
                            JSONObject data = jsonObject.getJSONObject("data");
                            final String url = data.optString("url");
                            Glide.with(getContext()).load(url).into(mF3Img);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    private void initOkHttp() {
        String filePath = Environment.getExternalStorageDirectory() + File.separator;
        File file = new File(filePath + "aa.jpg");
        if (!file.exists()) {
            Toast.makeText(getActivity(), "没有此文件", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(requestBody)
                .addFormDataPart("key", "b")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(multipartBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e.getCause().getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resurt = response.body().string();
                final UpLoad_Fragment_RootBean bean = new Gson().fromJson(resurt, UpLoad_Fragment_RootBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getActivity()).load(bean.getData().getUrl()).apply(RequestOptions.bitmapTransform(new RoundedCorners(25))).placeholder(R.mipmap.ic_launcher).into(mF3Img);
                        Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(myBroadcast);
    }
}
