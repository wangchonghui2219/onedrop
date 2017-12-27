package com.dlwx.onedrop.activitys;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.ResuleBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.SpUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 设置提现密码
 */
public class SetPayPWdActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_affpwd)
    EditText etAffpwd;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_set_pay_pwd);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
            initTabBar(toolBar);
            tvTitle.setText("设置提现密码");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @OnClick(R.id.btn_complete)
    public void onViewClicked() {
        String pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, "提现密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String affpwd = etAffpwd.getText().toString().trim();
        if (TextUtils.isEmpty(affpwd)) {
            vibrator.vibrate(50);
            Toast.makeText(ctx, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!affpwd.equals(pwd)) {
            etPwd.setText("");
            etAffpwd.setText("");
            Toast.makeText(ctx, "两次密码不一致,请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("tx_password",pwd);
        map.put("tx_password_again",affpwd);
    mPreenter.fetch(map,false, HtttpUrlUtils.SetPatPwd,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        ResuleBean resuleBean = gson.fromJson(s, ResuleBean.class);
        if (resuleBean.getCode() == 200) {
            finish();
            sp.edit().putString(SpUtils.is_tx_passwrord,"1").commit();
        }
        Toast.makeText(ctx, resuleBean.getResult(), Toast.LENGTH_SHORT).show();
    }
}
