package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改昵称
 */
public class RevampNickNameActivit extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.et_nickName)
    EditText etNickName;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_revamp_nick_name);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.revampName);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        String nickName = etNickName.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {

            Toast.makeText(ctx, "", Toast.LENGTH_SHORT).show();
            vibrator.vibrate(50);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("nickName",nickName);
        setResult(1001,intent);
        finish();
    }
}
