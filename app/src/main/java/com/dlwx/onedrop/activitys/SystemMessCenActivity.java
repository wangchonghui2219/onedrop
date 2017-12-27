package com.dlwx.onedrop.activitys;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.dlwx.baselib.base.BaseActivity;
import com.dlwx.baselib.presenter.Presenter;
import com.dlwx.onedrop.R;
import com.dlwx.onedrop.bean.SysMessDescBean;
import com.dlwx.onedrop.utiles.HtttpUrlUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dlwx.onedrop.base.MyApplication.Token;

/**
 * 消息详情
 */
public class SystemMessCenActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_titlename)
    TextView tvTitlename;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private String id;
    private String title;

    @Override
    protected void initView() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_system_mess_cen);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initTabBar(toolBar);
        tvTitle.setText(title);
        getdate();
    }

    private void getdate() {
        wch(id);
        Map<String,String> map = new HashMap<>();
        map.put("token",Token);
        map.put("id",id);
        mPreenter.fetch(map,true, HtttpUrlUtils.SysMessDesc,"");
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
        wch(s);
        Gson gson = new Gson();
        SysMessDescBean sysMessDescBean = gson.fromJson(s, SysMessDescBean.class);
        if (sysMessDescBean.getCode() == 200) {
            SysMessDescBean.BodyBean body = sysMessDescBean.getBody();

            tvTitlename.setText(body.getTitle());
            tvContent.setText( Html.fromHtml(String.valueOf(Html.fromHtml(body.getInfo()))));
        }else{
            Toast.makeText(ctx, sysMessDescBean.getResult(), Toast.LENGTH_SHORT).show();
        }
    }
}
