package com.dlwx.onedrop.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.model.SmsAuthBean;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.CountDownUtiles;
import com.dlwx.baselib.utiles.VerificationCodeUtiles;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.ImageAuthBean;
import com.dlwx.onedrop.utiles.AuthWindow;
import com.dlwx.onedrop.utiles.ButtonUtils;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册输入手机号
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_auth)
    TextView tvAuth;
    @BindView(R.id.et_authcode)
    EditText etAuthcode;
    @BindView(R.id.btn_next)
    Button btnNext;
    private String phone;
    private VerificationCodeUtiles codeUtiles;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.register);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @SuppressLint("MissingPermission")
    @OnClick({R.id.tv_auth, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_auth://获取验证码
                if (tvAuth.getText().equals("获取验证码") || tvAuth.getText().equals("重新发送"))
                getAuth();
                break;
            case R.id.btn_next://下一步
                String auth = etAuthcode.getText().toString().trim();
                if (TextUtils.isEmpty(auth)) {
                    Toast.makeText(ctx, "请输入验证码", Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(50);
                    return;
                }
                if ( codeUtiles == null) {
                    return;
                }
                SmsAuthBean.BodyBean body = codeUtiles.smsAuthBean.getBody();
                wch("body:"+body.getCode());
                if (auth.equals(body.getCode())) {
                    Intent intent = new Intent(ctx, RegisterMessActivity.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(ctx, "验证码输入错误", Toast.LENGTH_SHORT).show();
                }

                
                break;
        }
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {
            imageInputresult(s, gson);
        } else if (HttpType == 2) {

        }
    }

    /**
     * 输入图形验证返回数据
     *
     * @param s
     * @param gson
     */
    private void imageInputresult(String s, Gson gson) {
        ImageAuthBean imageAuthBean = gson.fromJson(s, ImageAuthBean.class);
        if (imageAuthBean.getCode() == 200) {
            ImageAuthBean.BodyBean body = imageAuthBean.getBody();
            String imgurl = body.getImgurl();
            imgname = body.getImgname();
            AuthWindow authWindow = new AuthWindow(ctx);
            authWindow.startShowPopu(ctx, tvAuth, imgurl);
            authWindow.setAuthListener(authListener);
        } else {
            Toast.makeText(ctx, imageAuthBean.getCode(), Toast.LENGTH_SHORT).show();
        }
    }
   private String imgname;
    /**
     * 输入图形验证码后返回的数据
     */
    private AuthWindow.AuthListener authListener = new AuthWindow.AuthListener() {
        @Override
        public void getResuktAuth(String auth) {
            if (!TextUtils.isEmpty(auth)) {
                CountDownUtiles countDownUtiles = new CountDownUtiles(tvAuth);
                if (tvAuth.getText().equals("获取验证码") || tvAuth.getText().equals("重新发送")) {
                    codeUtiles = new VerificationCodeUtiles(ctx, phone, 0, countDownUtiles,imgname,auth);
                    codeUtiles.sendVerificationCode(HtttpUrlUtils.GetAuth, loading);
                }
            }
        }

        @Override
        public void gainImg() {
            getAuth();
        }
    };


    /**
     * 获取图片验证码
     */
    private void getAuth() {
        phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            vibrator.vibrate(50);

            Toast.makeText(ctx, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ButtonUtils.isFastDoubleClick(R.id.tv_auth, 1000)) {
            HttpType = 1;
            Map<String, String> map = new HashMap<>();
            mPreenter.fetch(map, false, HtttpUrlUtils.ImgAuth, "");


        }
    }

}
