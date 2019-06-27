package com.example.practicehomework;
//刘子硕 2019/6/26/11:31 --- 15:17

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    /**
     * 跳过
     */
    private Button mButMain;
    private ImageView mIvMain;
    private CountDownTimer mTimer;
    String[] permissions = {READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE,INTERNET};
    private int REQUEST_FILE_CODE = 99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (EasyPermissions.hasPermissions(this, permissions)) {
            initView();

        } else {

            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(this, REQUEST_FILE_CODE, permissions)
                            .setRationale("请确认相关权限！！")
                            .setPositiveButtonText("ok")
                            .setNegativeButtonText("cancal")
//                            .setTheme(R.style.my_fancy_style)
                            .build());
        }
    }

    private void initView() {
        mButMain = (Button) findViewById(R.id.but_main);
        mButMain.setOnClickListener(this);
        mIvMain = (ImageView) findViewById(R.id.iv_main);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.curation);
        mIvMain.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                initTimer();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                finish();
                mTimer.cancel();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initTimer() {
        mTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mButMain.setText("跳过(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.but_main:
                startActivity(new Intent(this, Main2Activity.class));
                finish();
                mTimer.cancel();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        initView();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }
}
