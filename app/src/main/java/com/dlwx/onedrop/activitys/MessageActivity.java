package com.dlwx.onedrop.activitys;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.MessNumBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 消息
 */
public class MessageActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.ll_sysytem)
    LinearLayout llSysytem;
    @BindView(R.id.ll_ordermess)
    LinearLayout llOrdermess;
    @BindView(R.id.tv_sysnum)
    TextView tvSysnum;
    @BindView(R.id.tv_ordernum)
    TextView tvOrdernum;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(R.string.mess);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getNumber();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Presenter createPresenter() {
        return new Presenter(this);
    }
    @OnClick({R.id.ll_sysytem, R.id.ll_ordermess})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_sysytem:
                intent = new Intent(ctx, SystemMessActivity.class);
                intent.putExtra("title","系统消息");
                intent.putExtra("type","1");
                startActivity(intent);
                break;
            case R.id.ll_ordermess:
                intent = new Intent(ctx, SystemMessActivity.class);
                intent.putExtra("title","订单消息");
                intent.putExtra("type","2");
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取消息数量
     */
    private void getNumber() {
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        mPreenter.fetch(map,true, HtttpUrlUtils.MessNum,"");
    }

    @Override
    public void showData(String s) {
        disLoading();
        wch(s);
        Gson gson = new Gson();
        MessNumBean messNumBean = gson.fromJson(s, MessNumBean.class);
        if (messNumBean.getCode() == 200) {
            MessNumBean.BodyBean body = messNumBean.getBody();
            if (body.getSys_msgnum()>0) {
                tvSysnum.setText(body.getSys_msgnum()+"");
            }else{
                tvSysnum.setVisibility(View.GONE);
            }
            if (body.getOrder_msgnum()>0) {

                tvOrdernum.setText(body.getOrder_msgnum()+"");
            }else{
                tvOrdernum.setVisibility(View.GONE);
            }
        }else{
            Toast.makeText(ctx, messNumBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
}
