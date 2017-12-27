package com.dlwx.onedrop.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.baselib.view.CircleImageView;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.InviteCodeBean;
import com.dlwx.onedrop.bean.MyLeceBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.ShareDialogUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 我的級別
 */
public class MyLevelActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.btn_share)
    Button btnShare;
    @BindView(R.id.tv_infuse)
    TextView tvInfuse;
    private String qr_url;
    @BindView(R.id.tv_num)
    TextView tv_num;
    private InviteCodeBean.BodyBean body;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_level);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        getSupportActionBar().setHomeAsUpIndicator(com.dlwx.baselib.R.mipmap.icon_jiantoubaise);
        getData();
    }



    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_share)
    public void onViewClicked() {
        if (body.getIs_share() == 2) {
            Toast.makeText(ctx, R.string.shareString, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(qr_url)) {
            return;
        }
        ShareDialogUtils dialogUtils = new ShareDialogUtils(ctx,qr_url);
    }
    private void getData() {
        HttpType = 1;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true, HtttpUrlUtils.MyLeve,HtttpUrlUtils.MyLeve+Token);
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        if (HttpType == 1) {

            MyLeceBean myLeceBean = gson.fromJson(s, MyLeceBean.class);
            if (myLeceBean.getCode() == 200) {
                MyLeceBean.BodyBean body = myLeceBean.getBody();
                Glide.with(ctx).load(body.getHeader_pic()).into(ivHead);
                tvNickname.setText(body.getNickname());
                tvName.setText(body.getNew_vip());
                tvLevel.setText("我的级别: "+body.getVip_name());
                tv_num.setText("业务员"+body.getVip_yw_count()+"人,经理"+body.getVip_jl_count()+"人");
                getShareDate();
            }else{
                Toast.makeText(ctx, myLeceBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }else{
            InviteCodeBean inviteCodeBean = gson.fromJson(s, InviteCodeBean.class);
            if (inviteCodeBean.getCode() == 200) {
                body = inviteCodeBean.getBody();
                qr_url = body.getQr_url();
                
            }
        }

    }

    private void getShareDate() {
        HttpType = 2;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true, HtttpUrlUtils.GetCode,"");
    }
}
