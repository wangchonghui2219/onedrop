package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.base.MainActivity;
import com.dlwx.onedrop.utiles.SpUtils;

public class SplashActivity extends BaseActivity {

    private String first;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void initData() {
        first = sp.getString(SpUtils.First, "");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (TextUtils.isEmpty(first)) {
                    startActivity(new Intent(ctx,GuideActivity.class));

                }else{
                    startActivity(new Intent(ctx,MainActivity.class));
                }
                finish();
            }
        }
    };


}
