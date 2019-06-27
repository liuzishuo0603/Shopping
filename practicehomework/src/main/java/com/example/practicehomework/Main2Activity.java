package com.example.practicehomework;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 跳过
     */
    private Button mButMain2;
    private ImageView mIvMain2;
    private CountDownTimer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();
    }

    private void initData() {
        mTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mButMain2.setText("跳过(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(Main2Activity.this, Main3Activity.class));
                finish();
            }
        }.start();
    }

    private void initView() {
        mButMain2 = (Button) findViewById(R.id.but_main2);
        mButMain2.setOnClickListener(this);
        mIvMain2 = (ImageView) findViewById(R.id.iv_main2);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mIvMain2, "alpha", 1.0f, 0.5f, 1.0f);
        animator1.setDuration(2000);

        ObjectAnimator xAnimator2 = ObjectAnimator.ofFloat(mIvMain2, "scaleX", 1.0f, 0.5f, 1.0f);
        xAnimator2.setDuration(2000);

        ObjectAnimator yAnimator2 = ObjectAnimator.ofFloat(mIvMain2, "scaleY", 1.0f, 0.5f, 1.0f);
        yAnimator2.setDuration(2000);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mIvMain2, "rotation", 0f, 360f);
        animator3.setDuration(2000);

        AnimatorSet animator = new AnimatorSet();
        animator.play(xAnimator2).with(yAnimator2).after(animator1).before(animator3);
        animator.start();
       /* ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, 0.5f, 1.0f);
        animator1.setDuration(2000);

        ObjectAnimator xAnimator2 = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 0.5f, 1.0f);
        xAnimator2.setDuration(2000);

        ObjectAnimator yAnimator2 = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 0.5f, 1.0f);
        yAnimator2.setDuration(2000);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
        animator3.setDuration(2000);

        AnimatorSet animator = new AnimatorSet();
        animator.play(xAnimator2).with(yAnimator2).after(animator1).before(animator3);

        animator.start();*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.but_main2:
                startActivity(new Intent(this, Main3Activity.class));
                finish();
                mTimer.cancel();
                break;
        }
    }
}
