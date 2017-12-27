package com.dlwx.onedrop.activitys;

import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.utiles.CountDownUtiles;
import com.dlwx.baselib.utiles.VerificationCodeUtiles;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.ImageAuthBean;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.utiles.AuthWindow;
import com.dlwx.onedrop.utiles.ButtonUtils;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.SpUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码或忘记支付密码
 */
public class ForgitPwdActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_useraccount)
    EditText etUseraccount;
    @BindView(R.id.tv_auth)
    TextView tvAuth;
    @BindView(R.id.et_authcode)
    EditText etAuthcode;
    @BindView(R.id.et_newpwd)
    EditText etNewpwd;
    @BindView(R.id.et_affpwd)
    EditText etAffpwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String phone;
    private String title;
    private VerificationCodeUtiles codeUtiles;

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_forgit_pwd);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(title);
        if (!title.equals("更改登录密码")) {
            etNewpwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); //最大输入长度
            etAffpwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); //最大输入长度
            etNewpwd.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            etAffpwd.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        }
    }
    @Override
    protected void initListener() {

    }
    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick({R.id.tv_auth, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_auth:
                if (tvAuth.getText().equals("获取验证码") || tvAuth.getText().equals("重新发送"))
                getAuth();
                break;
            case R.id.btn_login:
                complete();
                break;
        }
    }

    private void complete() {
        phone = etUseraccount.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {

            Toast.makeText(ctx, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String authCode = etAuthcode.getText().toString().trim();
        if (TextUtils.isEmpty(authCode)) {

            Toast.makeText(ctx, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (codeUtiles == null) {
            Toast.makeText(ctx, "请先获取验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        String code = codeUtiles.smsAuthBean.getBody().getCode();
        if (!authCode.equals(code)) {
            Toast.makeText(ctx, "验证码输入错误", Toast.LENGTH_SHORT).show();
            return;
        }

        String newPwd = etNewpwd.getText().toString().trim();
        if (TextUtils.isEmpty(newPwd)) {

            Toast.makeText(ctx, "新密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String affPwd = etAffpwd.getText().toString().trim();
        if (TextUtils.isEmpty(affPwd)) {

            Toast.makeText(ctx, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!affPwd.equals(newPwd)) {
            etNewpwd.setText("");
            etAffpwd.setText("");
            Toast.makeText(ctx, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpType = 2;
        Map<String,String> map = new HashMap<>();
        map.put("user_phone",phone);
        map.put("password",newPwd);
        map.put("user_phone",phone);
        if (!title.equals("更改登录密码")||!title.equals("忘记登录密码")) {//提现密码
            map.put("is_tixian","1");
        }
        mPreenter.fetch(map,false,HtttpUrlUtils.ForgitPwd,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {
            imageInputresult(s, gson);
        } else if (HttpType == 2) {
            ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
            if (resuleBean.getCode() == 200) {
                if (!title.equals("更改登录密码")||!title.equals("忘记登录密码")) {//提现密码
                  sp.edit().putString(SpUtils.is_tx_passwrord,"1").commit();
                }
                finish();
            }
            Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
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
    }   private String imgname;
    /**
     * 输入图形验证码后返回的数据
     */
    private AuthWindow.AuthListener authListener = new AuthWindow.AuthListener() {
        @Override
        public void getResuktAuth(String auth) {
            if (!TextUtils.isEmpty(auth)) {


                CountDownUtiles countDownUtiles = new CountDownUtiles(tvAuth);
                if (tvAuth.getText().equals("获取验证码") || tvAuth.getText().equals("重新发送")) {
                    codeUtiles = new VerificationCodeUtiles(ctx, phone, 1, countDownUtiles,imgname,auth);
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
        phone = etUseraccount.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {

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
