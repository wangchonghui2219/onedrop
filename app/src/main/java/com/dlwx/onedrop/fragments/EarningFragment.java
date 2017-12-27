package com.dlwx.onedrop.fragments;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseFragment;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.InviteCodeBean;
import com.dlwx.onedrop.bean.MyEarnBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.dlwx.onedrop.utiles.ShareDialogUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 收益
 */
public class EarningFragment extends BaseFragment {
    @BindView(R.id.tv_ye)
    TextView tvYe;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_hist)
    TextView tvHist;
    @BindView(R.id.btn_share)
    Button btnShare;
    Unbinder unbinder;
    private String qr_url;
    private InviteCodeBean.BodyBean body;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_earning;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initDate() {
        getData();
    }

    private void getData() {
        HttpType = 1;
        Map<String,String> map =  new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true,HtttpUrlUtils.MyEarn,HtttpUrlUtils.MyEarn+Token);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();

        if (HttpType == 2) {
            InviteCodeBean inviteCodeBean = gson.fromJson(s, InviteCodeBean.class);
            if (inviteCodeBean.getCode() == 200) {
                body = inviteCodeBean.getBody();
                qr_url = body.getQr_url();

            }
        }else{
            MyEarnBean myEarnBean = gson.fromJson(s, MyEarnBean.class);
            if (myEarnBean.getCode() == 200) {
                MyEarnBean.BodyBean body = myEarnBean.getBody();
                tvYe.setText(body.getUse_price());
                tvDay.setText(body.getDay_shouyi());
                tvMonth.setText(body.getMonth_shouyi());
                tvHist.setText(body.getTotalprice());
                getShareDate();
            }else{
                Toast.makeText(ctx, myEarnBean.getResult(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int HttpType;
    private void getShareDate() {
        HttpType = 2;
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true, HtttpUrlUtils.GetCode,"");
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
}
