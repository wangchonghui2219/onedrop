package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.base.MyApplication;
import com.dlwx.onedrop.bean.LoginBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.SpUtils;
import com.dlwx.onedrop.utiles.wechatpay.wxapi.WxLoginutiles;
import com.dlwx.onedrop.wxapi.CallBackListenerUtiles;
import com.dlwx.onedrop.wxapi.QQUtiles;
import com.google.gson.Gson;
import com.tencent.tauth.Tencent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_useraccount)
    EditText etUseraccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forgit)
    TextView tvForgit;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.iv_qq)
    ImageView ivQq;
    @BindView(R.id.iv_wx)
    ImageView ivWx;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.login);
    }

    @Override
    protected void initListener() {
        CallBackListenerUtiles.setCallBackListener(callBackListener);
    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    @OnClick({R.id.tv_register, R.id.tv_forgit, R.id.btn_login, R.id.iv_qq, R.id.iv_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register://注册
                startActivity(new Intent(ctx,RegisterActivity.class));
                break;
            case R.id.tv_forgit://忘记密码
                startActivity(new Intent(ctx,ForgitPwdActivity.class).putExtra("title","忘记登录密码"));
                break;
            case R.id.btn_login://登录

                        type = 0;
                    loginPrepare();
                break;
            case R.id.iv_qq://qq登录
                type = 1;
                loginPrepare();
                break;
            case R.id.iv_wx://微信登录
                type = 2;
                loginPrepare();
                break;
        }
    }
    private int type;
    /**
     * 登录准备
     */
    private void loginPrepare() {
        if (type == 0) {//手机登陆
            String phone = etUseraccount.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(ctx, "用户名不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
             String pwd = etPwd.getText().toString().trim();
            if (TextUtils.isEmpty(pwd)) {
                Toast.makeText(ctx, "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            startLogin(0,phone,pwd,"","");
        }else if (type == 1) {//QQ

            if (QQUtiles.isQQClientAvailable(ctx)) {

                QQUtiles qqUtiles = new QQUtiles();
                qqUtiles.LoginQQ(ctx);
            }
        }else if (type == 2) {//WX

            WxLoginutiles wxLoginutiles = new WxLoginutiles(ctx);
            wxLoginutiles.loginWx(ctx);
        }

    }


    /**
     *
     * @param type 登录方式
     * @param user_phone 用户名  第三方登陆的是oppenID
     * @param password 密码 第三方登录的为昵称
     * @param imagePic 第三方登录的头像
     */
    private void startLogin(int type,String user_phone,String password,String imagePic,String gender) {
        Map<String,String> map = new HashMap<>();
        if (type == 0) {
            map.put("user_phone",user_phone);
            map.put("password",password);
        }else{
            wch(gender);
            map.put("openid",user_phone);
            map.put("nickname",password);
            map.put("header_pic",imagePic);
            map.put("login_type",type+"");
            map.put("sex",gender);
        }
        mPreenter.fetch(map,false, HtttpUrlUtils.Login,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch("登录"+s);
        Gson gson = new Gson();
        LoginBean loginBean = gson.fromJson(s, LoginBean.class);
        if (loginBean.getCode() == 200) {
            LoginBean.BodyBean body = loginBean.getBody();
            MyApplication.Token = body.getToken();
            MyApplication.HeadPic = body.getHeader_pic();
            sp.edit().putString(SpUtils.TOKEN,body.getToken()).commit();
            sp.edit().putString(SpUtils.telephone,body.getTelephone()).commit();
            sp.edit().putString(SpUtils.nickname,body.getNickname()).commit();
            sp.edit().putString(SpUtils.header_pic,body.getHeader_pic()).commit();
            sp.edit().putString(SpUtils.isqq,body.getIsqq()).commit();
            sp.edit().putString(SpUtils.User_ID,body.getUserid()).commit();
            sp.edit().putString(SpUtils.is_tx_passwrord,body.getIs_tx_passwrord()).commit();
            Set<String> tags = new HashSet<>();
            tags.add(body.getUserid());
            JPushInterface.setTags(getApplicationContext(),1,tags);
            finish();
        }
        Toast.makeText(ctx, loginBean.getResult(), Toast.LENGTH_SHORT).show();
    }

    private CallBackListenerUtiles.ShareCallBackListener callBackListener = new CallBackListenerUtiles.ShareCallBackListener() {
        @Override
        public void setComplete() {

        }
        @Override
        public void getUserInfo(String nickName, String imagePic, String openID,String gender) {
            wch(nickName);
                startLogin(type,openID,nickName,imagePic,gender);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, new QQUtiles().uiListener);

    }
}
