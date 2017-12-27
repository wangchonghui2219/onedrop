package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 填写注册信息
 */
public class RegisterMessActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_invitecode)
    EditText etIvitecode;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_affpwd)
    EditText etAffpwd;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_proto)
    TextView tvProto;
    private String phone;

    @Override
    protected void initView() {
        phone = getIntent().getStringExtra("phone");
        setContentView(R.layout.activity_register_mess);
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


    @Override
    public void showData(String s) {
        disLoading();
        Gson gson = new Gson();
        ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
        if (resuleBean.getCode() == 200) {
            finish();
        }
        Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
    }



    @OnClick({R.id.tv_proto, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_proto:
                startActivity(new Intent(ctx,HtmlActivity.class).putExtra("title","用户协议").putExtra("url",HtttpUrlUtils.UserAggress));
                break;
            case R.id.btn_next:
                String ivitecode = etIvitecode.getText().toString().trim();

                String userName = etUsername.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(ctx, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(50);
                    return;
                }
                String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(ctx, "密码不能为空", Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(50);
                    return;
                }
                String affpwd = etAffpwd.getText().toString().trim();
                if (TextUtils.isEmpty(affpwd)) {
                    Toast.makeText(ctx, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(50);
                    return;
                }
                if (!affpwd.equals(pwd)) {
                    Toast.makeText(ctx, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    etPwd.setText("");
                    etAffpwd.setText("");
                    return;
                }
                Map<String,String> map = new HashMap<>();
                map.put("user_phone",phone);
                map.put("password",pwd);
                map.put("p_exten_code",ivitecode);
                map.put("nickname",userName);
                mPreenter.fetch(map,false, HtttpUrlUtils.Register,"");
                break;
        }
    }
}
